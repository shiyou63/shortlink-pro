package com.dddang.shortlinkpro.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dddang
 * @create 2025-04-14  下午6:14
 */
@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    public ApiResponse(int code, String message, Object o) {
    }

    // 成功响应（不带数据）
    public static ApiResponse<Void> success() {
        return of(ResponseStatus.SUCCESS, null);
    }
    // 新增：支持直接传入code和message的失败方法
    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    // 成功响应（带数据）
    public static <T> ApiResponse<T> success(T data) {
        return of(ResponseStatus.SUCCESS, data);
    }

    // 失败响应
    public static <T> ApiResponse<T> fail(ResponseStatus status) {
        return of(status, null);
    }

    // 通用构建方法
    public static <T> ApiResponse<T> of(ResponseStatus status, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.code = status.getCode();
        response.message = status.getMessage();
        response.data = data;
        return response;
    }

    public static ApiResponse<String> success(String 短链创建成功, String shortCode) {
        ApiResponse<String> response = new ApiResponse<>();
        response.code = ResponseStatus.SUCCESS.getCode();
        response.message = 短链创建成功;
        response.data = shortCode;
        return response;
    }
}