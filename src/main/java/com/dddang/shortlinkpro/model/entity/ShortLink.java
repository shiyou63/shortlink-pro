package com.dddang.shortlinkpro.model.entity;

import javax.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author dddang
 * @create 2025-04-13  下午2:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder(toBuilder = true)
@Entity
@Table(name = "short_link")
public class ShortLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_code", unique = true, length = 8)
    private String shortCode;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;

    @Column(name = "custom_code", length = 8)
    private String customCode;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "access_count", columnDefinition = "BIGINT DEFAULT 0") // 改为BIGINT
    private Long accessCount;

    @Column(name = "deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

    //过期时间
    @Column(name = "expire_time")
    private LocalDateTime expireTime;
    //temp字段用于判断是否为临时短链，默认为false
    @Column(name = "is_temp", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean temp;



    public ShortLink(String shortCode, String originalUrl) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
    }


    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}