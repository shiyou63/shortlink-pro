
package com.dddang.shortlinkpro.service.impl;

import com.dddang.shortlinkpro.exception.ShortLinkException;
import com.dddang.shortlinkpro.model.entity.ErrorCode;
import com.dddang.shortlinkpro.model.entity.ShortLink;
import com.dddang.shortlinkpro.model.dto.ShortLinkCreateRequest;
import com.dddang.shortlinkpro.model.vo.ShortLinkVO;
import com.dddang.shortlinkpro.repository.ShortLinkRepository;
import com.dddang.shortlinkpro.service.ShortLinkService;
import com.google.common.hash.BloomFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static com.dddang.shortlinkpro.util.RandomCodeUtils.generateRandomCode;

/**
 * @author 17470
 * @Description: 短链服务实现类
 * 功能：
 * 1. 生成短链
 * 2. 解析短链
 * 3. 删除短链
 * 4. 短链列表
 * 5. 统计访问量
 * 6. 缓存优化
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl implements ShortLinkService {

    @Autowired
   ShortLinkRepository repository;
    @Autowired
   RedisTemplate<String, Object> redisTemplate;
    @Autowired
   RedissonClient redisson;
    @Autowired
    BloomFilter<String> bloomFilter;

    // 常量配置
    private static final String CACHE_PREFIX = "slink:";
    private static final String COUNTER_PREFIX = "counter:";

    @Override
    @Transactional
    public String generateShortLink(ShortLinkCreateRequest request) {
        //生成或验证短码
        String shortCode = resolveShortCode(request);

        // 加锁执行核心操作
        return executeWithLock(shortCode, () -> {
            // 锁内二次校验（防止并发冲突）
            validateShortCodeUniqueness(shortCode, request.getPersist());

            // 存储数据
            persistShortLink(request, shortCode);

            return shortCode;
        });
    }


    private void persistShortLink(ShortLinkCreateRequest request, String shortCode) {
        if (Boolean.TRUE.equals(request.getPersist())) {
            // 持久化到数据库，并删除可能存在的临时缓存
            ShortLink entity = new ShortLink(shortCode, request.getOriginalUrl());
            repository.save(entity);

            // 删除临时缓存（如果存在）
            String tempKey = "temp:shortlink:" + shortCode;
            try {
                redisTemplate.delete(tempKey);
            } catch (Exception e) {
                log.error("删除临时缓存失败: key={}", tempKey, e);
            }
        } else {
            // 写入临时缓存，固定5分钟过期
            String tempKey = "temp:shortlink:" + shortCode;
            redisTemplate.opsForValue().set(
                    tempKey,
                    request.getOriginalUrl(),
                    Duration.ofMinutes(5) // 强制固定5分钟
            );
        }
    }

    // 加锁执行核心操作
    private <T> T executeWithLock(String shortCode, Supplier<T> action) {
        RLock lock = redisson.getLock("lock:shortcode:" + shortCode);
        boolean locked = false;
        try {
            // 尝试获取锁（等待1秒，锁持有时间不设置，由看门狗自动续期）
            locked = lock.tryLock(1, -1, TimeUnit.SECONDS);
            if (locked) {
                return action.get();
            }
            throw new ShortLinkException("系统繁忙，请稍后重试");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ShortLinkException("操作被中断");
        } catch (Exception e) {
            throw new ShortLinkException("系统异常：" + e.getMessage());
        } finally {
            // 仅在成功获取锁时释放
            if (locked) {
                try {
                    lock.unlock();
                } catch (IllegalMonitorStateException e) {
                    // 日志记录，非核心异常
                    log.warn("锁释放异常：线程未持有锁");
                }
            }
        }
    }

    @Override
    public String resolveShortLink(String shortCode) {
        // 布隆过滤器预检
        if (!bloomFilter.mightContain(shortCode)) {
            throw new ShortLinkException(ErrorCode.SHORT_CODE_NOT_EXIST);
        }

        // 多级缓存查询
        return getFromCache(shortCode)
                .orElseGet(() -> loadFromDbAndCache(shortCode));
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ShortLinkVO> listShortLinks(Pageable pageable) {
        // 查询数据库实体分页
        Page<ShortLink> entityPage = repository.findAll(pageable);

        // 转换为 VO（直接调用 fromEntity 方法）
        return entityPage.map(ShortLinkVO::fromEntity);
    }

    @Override
    @Transactional
    public void deleteShortLink(String shortCode) throws ShortLinkException {
        //直接删除数据库记录
        repository.deleteByShortCode(shortCode);

        //删除缓存
        redisTemplate.delete(CACHE_PREFIX + shortCode);

        //删除统计数据
        redisTemplate.delete(COUNTER_PREFIX + shortCode);

    }

    @Override
    @Transactional
    public Long recordVisit(String shortCode) {
        RedisAtomicLong counter = new RedisAtomicLong(
                COUNTER_PREFIX + shortCode,
                Objects.requireNonNull(redisTemplate.getConnectionFactory())
        );
        return counter.incrementAndGet();
    }
/*-- ------------------------------私有方法-----------------------------------------------*/

    private String resolveShortCode(ShortLinkCreateRequest request) {
        return StringUtils.hasText(request.getCustomCode())
                ? handleCustomCode(request.getCustomCode(), request.getPersist())
                : generateUniqueRandomCode(request.getPersist());
    }
    private String handleCustomCode(String customCode, Boolean persist) {
        RLock lock = redisson.getLock("lock:shortcode:custom:" + customCode);
        try {
            if (lock.tryLock(1, 30, TimeUnit.SECONDS)) {
                if (isShortCodeExists(customCode, persist)) {
                    throw new ShortLinkException(ErrorCode.SHORT_CODE_CONFLICT, "短码已被占用");
                }
                return customCode;
            }
            throw new ShortLinkException(ErrorCode.SYSTEM_BUSY, "系统繁忙，请稍后重试");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ShortLinkException(ErrorCode.OPERATION_INTERRUPTED, "操作被中断");
        } finally {
            lock.unlock();
        }
    }
    //验证是否存在自定义短码
    private boolean isShortCodeExists(String customCode, Boolean persist) {
        return repository.existsByCustomCode(customCode) ||
                (persist == null || persist && repository.existsByShortCode(customCode));
    }

    private String generateUniqueRandomCode(Boolean persist) {
        int maxAttempts = 5;
        for (int i = 0; i < maxAttempts; i++) {
            String code = generateRandomCode();
            if (!isShortCodeExists(code, persist)) {
                return code;
            }
        }
        throw new ShortLinkException(ErrorCode.GENERATE_CODE_FAILED, "生成短码失败，请尝试自定义");
    }

    private void validateShortCodeUniqueness(String shortCode, Boolean persist) {
        if (isShortCodeExists(shortCode, persist)) {
            throw new ShortLinkException(ErrorCode.SHORT_CODE_CONFLICT, "短码已存在");
        }
    }

    private Optional<String> getFromCache(String shortCode) {
        try {
            Object cached = redisTemplate.opsForValue().get(CACHE_PREFIX + shortCode);
            return Optional.ofNullable((String) cached);
        } catch (ClassCastException e) {
            log.warn("Cache type mismatch for {}", shortCode);
            return Optional.empty();
        }
    }

    private String loadFromDbAndCache(String shortCode) {
        ShortLink link = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new ShortLinkException(ErrorCode.SHORT_CODE_NOT_EXIST));

        // 缓存原始URL
        redisTemplate.opsForValue().set(
                CACHE_PREFIX + shortCode,
                link.getOriginalUrl(),
                Duration.ofHours(24)
        );
        return link.getOriginalUrl();
    }
}