package com.dddang.shortlinkpro.controller;

import com.dddang.shortlinkpro.common.ApiResponse;
import com.dddang.shortlinkpro.model.dto.LinkCreateRequest;
import com.dddang.shortlinkpro.model.entity.ShortLink;
import com.dddang.shortlinkpro.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

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

    @GetMapping("/links/{shortCode}")
    public ApiResponse<String> getOriginalUrl(@PathVariable String shortCode) {
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