package com.dddang.shortlinkpro.common;

/**
 * @author dddang
 * @create 2025-04-14  下午6:13
 */
public enum ResponseStatus {
    // 基础状态码
    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "请求参数错误"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_ERROR(500, "系统繁忙"),

    // 业务状态码（从600开始）
    LINK_NOT_FOUND(601, "短链接不存在"),
    INVALID_URL(602, "URL必须以http或https开头");

    private final int code;
    private final String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getter方法
    public int getCode() { return code; }
    public String getMessage() { return message; }
}
