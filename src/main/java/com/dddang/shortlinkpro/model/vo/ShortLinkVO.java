package com.dddang.shortlinkpro.model.vo;

import com.dddang.shortlinkpro.model.entity.ShortLink;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Description: 短链接视图对象
 * @Author : dddang
 * @Date :2025-04-22  下午3:04
 */
@Data
@Accessors(chain = true) // 启用链式调用
@Schema(description = "短链接视图对象")
public class ShortLinkVO {
    @Schema(description = "短码", example = "abc123")
    private String shortCode;

    @Schema(description = "原始链接")
    private String originalUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "访问总量")
    private Long totalVisits;

    // 全参数构造函数（Lombok的@AllArgsConstructor会自动生成）

    public static ShortLinkVO fromEntity(ShortLink entity) {
        return new ShortLinkVO()
                .setShortCode(entity.getShortCode())
                .setOriginalUrl(entity.getOriginalUrl())
                .setCreatedAt(entity.getCreatedAt())
                .setTotalVisits(entity.getAccessCount());
    }
}
