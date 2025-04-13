package com.dddang.shortlinkpro.service;

import com.dddang.shortlinkpro.model.entity.ShortLink;

import java.util.List;

/**
 * @author dddang
 * @create 2025-04-13  下午2:56
 */
public interface ShortLinkService {
    // 创建短链
     String createShortLink(String originalUrl);
     // 根据短链获取原始URL
     String getOriginalUrl(String shortCode);
     //显示全部短链
     List<ShortLink> getAllShortLinks();
 public boolean deleteByShortCode(String shortCode);

}
