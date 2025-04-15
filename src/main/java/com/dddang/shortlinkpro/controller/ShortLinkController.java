package com.dddang.shortlinkpro.controller;

<<<<<<< HEAD
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
=======
import com.dddang.shortlinkpro.model.entity.ShortLink;
import com.dddang.shortlinkpro.service.ShortLinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
>>>>>>> 5cdac6da93f666c791bf28adb2e7b088aaebfee5

/**
 * @author dddang
 * @create 2025-04-13  下午2:56
 */
<<<<<<< HEAD
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
=======
@RestController
@RequestMapping("/api")
public class ShortLinkController {
    private final ShortLinkService shortLinkService;

    public ShortLinkController(ShortLinkService shortLinkService) {
        this.shortLinkService = shortLinkService;
    }

    //创建短链接
    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> createShortLink(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        String shortCode = shortLinkService.createShortLink(originalUrl);
        return ResponseEntity.ok(Collections.singletonMap("shortCode", shortCode));
    }
    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirect(@PathVariable String shortCode) {
        String originalUrl = shortLinkService.getOriginalUrl(shortCode);
        if (originalUrl != null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", originalUrl)
                    .build();
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "短链接不存在");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    //显示全部链接
    @GetMapping("/links")
    public ResponseEntity<List<ShortLink>> getAllShortLinks() {
        return ResponseEntity.ok(shortLinkService.getAllShortLinks());
    }
    //删除短链
    @DeleteMapping("/links/{shortCode}")
    public ResponseEntity<Map<String, String>> deleteShortLink(
            @PathVariable String shortCode) {
        boolean deleted = shortLinkService.deleteByShortCode(shortCode);
        if (deleted) {
            return ResponseEntity.ok(Collections.singletonMap("message", "删除成功"));
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "短链接不存在");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
>>>>>>> 5cdac6da93f666c791bf28adb2e7b088aaebfee5
}