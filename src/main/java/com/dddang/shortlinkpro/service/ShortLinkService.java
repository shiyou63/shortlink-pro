package com.dddang.shortlinkpro.service;

import com.dddang.shortlinkpro.model.dto.LinkDetail;
import com.dddang.shortlinkpro.model.entity.ShortLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author dddang
 * @create 2025-04-13  下午2:56
 */
public interface ShortLinkService {

    String createShortLink(String originalUrl);
    String getOriginalUrl(String shortCode);
    List<ShortLink> getAllShortLinks();
    boolean deleteByShortCode(String shortCode);
}

