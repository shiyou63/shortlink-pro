package com.dddang.shortlinkpro.model.dto;

import com.dddang.shortlinkpro.annotation.NotNullWhenPersistFalse;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.time.LocalDateTime;


/**
 * @Description: 创建短链请求参数
 * @Author : dddang
 * @Date :2025-04-22  下午3:04
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ShortLinkCreateRequest {
    @NotBlank(message = "原始URL不能为空")
    @URL(message = "URL协议必须为HTTP或HTTPS")
    @Pattern(regexp = "^(https?://).*", message = "URL格式不合法")
    private String originalUrl;

    @Pattern(
            regexp = "^$|^[A-Za-z0-9]{4,8}$",  // 允许空值或4-8位字母数字
            message = "短码必须为4-8位字母数字组合或留空"
    )
    private String customCode;

    @NotNull(message = "持久化选项不能为空")
    private Boolean persist;
}
