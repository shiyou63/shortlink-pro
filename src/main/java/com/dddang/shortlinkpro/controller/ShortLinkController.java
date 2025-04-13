package com.dddang.shortlinkpro.controller;

import com.dddang.shortlinkpro.model.entity.ShortLink;
import com.dddang.shortlinkpro.service.ShortLinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dddang
 * @create 2025-04-13  下午2:56
 */
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
}