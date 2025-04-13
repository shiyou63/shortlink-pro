package com.dddang.shortlinkpro.service.impl;

import com.dddang.shortlinkpro.exception.ShortLinkException;
import com.dddang.shortlinkpro.mapper.ShortLinkMapper;
import com.dddang.shortlinkpro.model.entity.ShortLink;
import com.dddang.shortlinkpro.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author 17470
 */
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl implements ShortLinkService {

    private final ShortLinkMapper shortLinkMapper;

    @Override
    public String createShortLink(String originalUrl) {
        // 基础校验：URL格式
        if (originalUrl == null || !originalUrl.startsWith("http")) {
            throw new IllegalArgumentException("URL必须以http或https开头");
        }

        // 生成短码（6位MD5哈希）
        String baseCode = DigestUtils.md5Hex(originalUrl).substring(0, 6);
        String shortCode = baseCode;
        int retryCount = 0;

        // 处理哈希冲突（最多重试3次）
        while (shortLinkMapper.existsByShortCode(shortCode) && retryCount < 3) {
            shortCode = baseCode + "_" + (++retryCount);
        }

        // 构建实体对象
        ShortLink link = ShortLink.builder()
                .shortCode(shortCode)
                .originalUrl(originalUrl)
                .createdAt(LocalDateTime.now())
                .accessCount(0)
                .build();

        // 插入数据库
        shortLinkMapper.insert(link);
        return shortCode;
    }

    @Override
    public String getOriginalUrl(String shortCode) {
        // 查询并处理空值
        Optional<ShortLink> optionalLink = shortLinkMapper.findByShortCode(shortCode);
        if (!optionalLink.isPresent()) {    // 未找到
            return null;
        }

        // 原子操作更新访问次数
        ShortLink link = optionalLink.get();
        shortLinkMapper.incrementAccessCount(link.getId());
        return link.getOriginalUrl();
    }

    @Override
    public List<ShortLink> getAllShortLinks() {
        // 直接返回全部数据（小数据量适用）
        return shortLinkMapper.findAll();
    }

    @Override
    public boolean deleteByShortCode(String shortCode) {
        // 先检查是否存在
        if (!shortLinkMapper.existsByShortCode(shortCode)) {
            throw new ShortLinkException("短链接不存在: " + shortCode);
        }
        // 执行删除并返回结果
        return shortLinkMapper.deleteByShortCode(shortCode) > 0;
    }
}
