package com.dddang.shortlinkpro.controller;

import com.dddang.shortlinkpro.common.ApiResponse;
import com.dddang.shortlinkpro.model.dto.LinkCreateRequest;
import com.dddang.shortlinkpro.model.entity.ShortLink;
import com.dddang.shortlinkpro.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dddang.shortlinkpro.common.ResponseStatus.LINK_NOT_FOUND;

/**
 * @author dddang
 * @create 2025-04-13  下午2:56
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShortLinkController {
    private final ShortLinkService shortLinkService;

    @PostMapping("/shorten")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<String> createShortLink(
            @RequestBody @Valid LinkCreateRequest request
    ) {
        log.info("创建短链接请求: {}", request.getUrl());
        String shortCode = shortLinkService.createShortLink(request.getUrl());
        return ApiResponse.success("短链创建成功", shortCode);
    }

    // 新增跳转接口
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = shortLinkService.getOriginalUrl(shortCode);
        if (originalUrl == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    // 原接口修改路径
    @GetMapping("/links/{shortCode}/detail")
    public ApiResponse<String> getOriginalUrlDetail(@PathVariable String shortCode) {
        String url = shortLinkService.getOriginalUrl(shortCode);
        return url != null ?
                ApiResponse.success(url) :
                ApiResponse.fail(LINK_NOT_FOUND);
    }
        //显示所有短链
        @GetMapping("/links")
        public ApiResponse<List<ShortLink>> getAllShortLinks() {
            List<ShortLink> links = shortLinkService.getAllShortLinks();
            return ApiResponse.success(links);
        }

        @DeleteMapping("/links/{shortCode}")
        public ApiResponse<Void> deleteShortLink(@PathVariable String shortCode) {
            boolean success = shortLinkService.deleteByShortCode(shortCode);
            return success ?
                    ApiResponse.success() :
                    ApiResponse.fail(LINK_NOT_FOUND);
        }
}