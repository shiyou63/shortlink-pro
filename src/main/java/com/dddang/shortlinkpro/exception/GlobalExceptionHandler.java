package com.dddang.shortlinkpro.exception;

import com.dddang.shortlinkpro.model.entity.R;
import com.dddang.shortlinkpro.model.entity.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author dddang
 * @create 2025-04-14  下午6:16
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ShortLinkException.class)
    public ResponseEntity<R<?>> handleShortLinkException(ShortLinkException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(R.fail(ex.getErrorCode()));
    }
}