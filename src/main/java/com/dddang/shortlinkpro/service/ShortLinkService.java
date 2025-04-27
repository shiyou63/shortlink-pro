package com.dddang.shortlinkpro.service;

import com.dddang.shortlinkpro.exception.ShortLinkException;
import com.dddang.shortlinkpro.model.dto.ShortLinkCreateRequest;
import com.dddang.shortlinkpro.model.vo.ShortLinkVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;


/**
 * @author dddang
 * @create 2025-04-13  下午2:56
 */
public interface ShortLinkService {
    /**
     * 生成短链接（支持自定义短码）
     * @param request 包含原始URL、自定义短码等参数
     * @return 生成的短码
     */
    String generateShortLink(ShortLinkCreateRequest request) throws ShortLinkException;

    /**
     * 解析短链接获取原始URL（带多级缓存）
     * @param shortCode 短码
     * @return 原始长链接
     */
    String resolveShortLink(String shortCode) throws ShortLinkException;

    /**
     * 记录短链接访问量（异步）
     *
     * @param shortCode 短码
     */
    @Async
    Long recordVisit(String shortCode);

    /**
     * 分页查询短链接列表（支持创建时间排序）
     * @param page 分页参数
     * @return 分页数据
     */
    Page<ShortLinkVO> listShortLinks(Pageable page);

    /**
     * 删除短链接（清理缓存+数据库）
     * @param shortCode 短码
     */
    void deleteShortLink(String shortCode) throws ShortLinkException;
}
