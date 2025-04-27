package com.dddang.shortlinkpro.controller;

import com.dddang.shortlinkpro.annotation.AccessLimit;
import com.dddang.shortlinkpro.model.entity.R;
import com.dddang.shortlinkpro.model.dto.ShortLinkCreateRequest;
import com.dddang.shortlinkpro.model.vo.ShortLinkVO;
import com.dddang.shortlinkpro.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * @author dddang
 * @create 2025-04-13  下午2:56
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/short-links")
public class ShortLinkController {
    @Autowired
    ShortLinkService shortLinkService;

    /**
     * 创建短链
     * @param request 创建请求
     * 原始URL不能为空，URL格式非法，自定义短码，过期时间
     *  生成次数限制
     * @return 短链
     */
    @PostMapping("/generate")
    @AccessLimit(seconds = 60, maxCount = 10, msg = "每分钟最多生成10次短链")
    public R<Map<String, Object>> createShortLink(
            @Valid @RequestBody ShortLinkCreateRequest request) {
        // 调用Service生成短链
        String shortCode = shortLinkService.generateShortLink(request);

        // 构建响应结果
        Map<String, Object> result = new HashMap<>();
        result.put("shortCode", shortCode);
        result.put("originalUrl", request.getOriginalUrl());

        // 非持久化时添加过期时间
        if (Boolean.FALSE.equals(request.getPersist())) {
            result.put("expireAt", LocalDateTime.now().plusMinutes(5));
        }

        return R.ok(result);
    }
    /**
     * 重定向到原始URL（新增接口）
     * 前端访问示例：GET /api/short-links/r/abc123
     */
    @GetMapping("/redirect/{shortCode}")
    public void redirectToOriginal(
            @PathVariable String shortCode,
            HttpServletResponse response) throws IOException {
        String originalUrl = shortLinkService.resolveShortLink(shortCode);
        response.sendRedirect(originalUrl); // 302重定向
    }

    /**
     * 解析短链
     * @param shortCode 短码
     * @return 原始链接
     */
    @GetMapping("/resolve/{shortCode}")
    public R<String> resolveShortLink(@PathVariable String shortCode) {
        String originalUrl = shortLinkService.resolveShortLink(shortCode);
        return R.ok(originalUrl);
    }
    /**
     * 分页列表
     * @param pageable 分页请求
     * @return 短链列表
     */

    @GetMapping
    public R<Page<ShortLinkVO>> listShortLinks(
            @PageableDefault(size = 20) Pageable pageable) {
        return R.ok(shortLinkService.listShortLinks(pageable));
    }

    /**
     * 删除短链
     * @param shortCode 短码
     * @return 成功或失败
     */
    @DeleteMapping("/{shortCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public R<Void> deleteShortLink(@PathVariable String shortCode) {
        shortLinkService.deleteShortLink(shortCode);
        return R.ok();
    }

    /**
     * 记录访问量
     * @param shortCode 短码
     * @return 访问量
     */
    @GetMapping("/stats/{shortCode}")
    public R<Long> getAccessStats(@PathVariable String shortCode) {
        return R.ok(shortLinkService.recordVisit(shortCode));
    }
}