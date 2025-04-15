package com.dddang.shortlinkpro.model.entity;

<<<<<<< HEAD
import lombok.*;
=======
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
>>>>>>> 5cdac6da93f666c791bf28adb2e7b088aaebfee5

import java.time.LocalDateTime;

/**
 * @author dddang
 * @create 2025-04-13  下午2:49
 */
@Data
@Getter
@Setter
<<<<<<< HEAD
@Builder
@NoArgsConstructor
@AllArgsConstructor

=======
>>>>>>> 5cdac6da93f666c791bf28adb2e7b088aaebfee5
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
