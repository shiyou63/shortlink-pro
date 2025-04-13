package com.dddang.shortlinkpro.exception;

import com.dddang.shortlinkpro.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author dddang
 * @create 2025-04-14  下午6:16
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理业务异常（明确返回ApiResponse<Void>）
    @ExceptionHandler(ShortLinkException.class)
    public ApiResponse<Void> handleShortLinkException(ShortLinkException ex) {
        log.warn("业务异常: {}", ex.getMessage());
        return ApiResponse.fail(400, ex.getMessage());
    }

    // 处理系统异常（使用<?>通配符）
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception ex) {
        log.error("系统错误: {}", ex.getMessage(), ex);
        return ApiResponse.fail(500, "系统繁忙，请稍后再试");
    }
}
