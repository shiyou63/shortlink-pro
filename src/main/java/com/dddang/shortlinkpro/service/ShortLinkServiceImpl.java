package com.dddang.shortlinkpro.service;

import com.dddang.shortlinkpro.mapper.ShortLinkMapper;
import com.dddang.shortlinkpro.model.entity.ShortLink;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
        @author  dddang
        @create  2025-04-13  下午3:55

 */
@Service
public class ShortLinkServiceImpl implements ShortLinkService {
    private final ShortLinkMapper shortLinkMapper;

    public ShortLinkServiceImpl(ShortLinkMapper shortLinkMapper) {
        this.shortLinkMapper = shortLinkMapper;
    }

    @Override
    public String createShortLink(String originalUrl) {
        String shortCode = DigestUtils.md5Hex(originalUrl).substring(0, 6);
        ShortLink link = new ShortLink();
        link.setShortCode(shortCode);
        link.setOriginalUrl(originalUrl);
        shortLinkMapper.insert(link);
        return shortCode;
    }

    @Override
    public String getOriginalUrl(String shortCode) {
        ShortLink link = shortLinkMapper.findByShortCode(shortCode);
        if (link != null) {
            shortLinkMapper.incrementAccessCount(link.getId());
            return link.getOriginalUrl();
        }
        return null;
    }

    @Override
    public List<ShortLink> getAllShortLinks() {
        return shortLinkMapper.findAll();
    }

    @Override
    public boolean deleteByShortCode(String shortCode) {
        return shortLinkMapper.deleteByShortCode(shortCode) > 0;
    }
}