package com.dddang.shortlinkpro.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author dddang
 * @create 2025-04-13  下午2:49
 */
@Data
@Getter
@Setter
public class ShortLink {
    // 主键
    private Long id;
    // 短链
    private String shortCode;
    // 原始链接
    private String originalUrl;
    // 创建时间
    private LocalDateTime createdAt;
    // 访问次数
    private Integer accessCount;
}
