package com.dddang.shortlinkpro.exception;

import com.dddang.shortlinkpro.model.entity.ErrorCode;
/**
 * 自定义异常类
 */
public class ShortLinkException extends RuntimeException {
    private final ErrorCode errorCode;

    public ShortLinkException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ShortLinkException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ShortLinkException(String message) {
        super(message);
        this.errorCode = ErrorCode.SUCCESS;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
