package com.dddang.shortlinkpro.model.entity;

import com.dddang.shortlinkpro.exception.ShortLinkException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description:
 * @Author : dddang
 * @Date :2025-04-22  下午4:12
 */
@Data
@NoArgsConstructor
@Accessors(chain = true) // 支持链式调用
@Builder
@Schema(description = "统一响应结构体")
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "状态码", example = "200")
    private int code;

    @Schema(description = "提示信息", example = "操作成功")
    private String msg;

    @Schema(description = "业务数据")
    private T data;

    @Schema(description = "时间戳", example = "1672502400000")
    private long timestamp = System.currentTimeMillis();

    // 私有全参构造函数（供Builder使用）
    private R(int code, String msg, T data, long timestamp) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = timestamp;
    }

    /*-------------------- 静态工厂方法 --------------------*/
    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(T data) {
        return new R<T>()
                .setCode(ErrorCode.SUCCESS.getCode())
                .setMsg(ErrorCode.SUCCESS.getMessage())
                .setData(data);
    }

    public static <T> R<T> fail(ErrorCode errorCode) {
        return new R<T>()
                .setCode(errorCode.getCode())
                .setMsg(errorCode.getMessage());
    }

    public static <T> R<T> validateFail(String message) {
        return new R<T>()
                .setCode(ErrorCode.BAD_REQUEST.getCode())
                .setMsg(message);
    }

    public static <T> R<T> exception(ShortLinkException ex) {
        return new R<T>()
                .setCode(ex.getErrorCode().getCode())
                .setMsg(ex.getMessage());
    }

    public static R create(int i, String msg) {
        return new R()
                .setCode(i)
                .setMsg(msg);
    }
}