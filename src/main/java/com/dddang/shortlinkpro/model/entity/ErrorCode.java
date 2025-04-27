package com.dddang.shortlinkpro.model.entity;

import java.util.Arrays;

/**
 * @Description:
 * @Author : dddang
 * @Date :2025-04-22  下午4:12
 */
public enum ErrorCode {
    // 成功状态
    SUCCESS(200, "操作成功"),

    // 客户端错误
    BAD_REQUEST(400, "请求参数错误"),

    // 服务端错误
    INTERNAL_ERROR(500, "系统内部错误"),
    SYSTEM_BUSY(503, "系统繁忙"),
    SHORT_CODE_NOT_EXIST(5001, "短码不存在" ),
    SHORT_CODE_CONFLICT( 5002, "短码冲突" ),
    OPERATION_INTERRUPTED(  5003, "操作中断" ),
    GENERATE_CODE_FAILED( 5004, "生成短码失败" );

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // 根据状态码查找枚举
    public static ErrorCode fromCode(int code) {
        return Arrays.stream(values())
                .filter(e -> e.code == code)
                .findFirst()
                .orElse(INTERNAL_ERROR);
    }

    // Getters
    public int getCode() { return code; }
    public String getMessage() { return message; }
}

