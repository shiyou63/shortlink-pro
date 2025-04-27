package com.dddang.shortlinkpro.repository;

import com.dddang.shortlinkpro.model.entity.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * @Description: 短链数据访问层
 * @Author : dddang
 * @Date :2025-04-22  下午3:05
 */
public interface ShortLinkRepository extends JpaRepository<ShortLink, Long> {

    // 基础查询（无锁）
    @Query("SELECT s FROM ShortLink s WHERE s.shortCode = :code AND s.deleted = false")
    Optional<ShortLink> findByShortCode(@Param("code") String shortCode);

    // 存在性检查（使用覆盖索引）
    @Query("SELECT COUNT(s) > 0 FROM ShortLink s WHERE s.shortCode = :code")
    boolean existsByShortCode(@Param("code") String code);
    // 物理删除
    @Query("DELETE FROM ShortLink WHERE shortCode = :code")
    void deleteByShortCode(@Param("code") String code);
    //查询自定义短码是否存在
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
            "FROM ShortLink s WHERE s.customCode = :customCode")
    boolean existsByCustomCode(@Param("customCode") String customCode);
}

