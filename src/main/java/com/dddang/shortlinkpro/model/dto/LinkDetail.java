package com.dddang.shortlinkpro.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkDetail {
    private String shortCode;
    private String originalUrl;
    private Integer accessCount;
    private LocalDateTime createdAt;
    private LocalDateTime lastAccessedAt;
}