package com.dddang.shortlinkpro.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

/**
 * @author dddang
 * @create 2025-04-14  下午6:15
 */
@Data
public class LinkCreateRequest {
    @NotBlank(message = "URL不能为空")
    @URL(message = "URL格式不合法")
    private String url;
}
