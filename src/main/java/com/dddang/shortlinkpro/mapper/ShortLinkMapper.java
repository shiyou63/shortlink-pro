package com.dddang.shortlinkpro.mapper;

import com.dddang.shortlinkpro.model.entity.ShortLink;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author dddang
 * @create 2025-04-13  下午2:52
 */
@Mapper
public interface ShortLinkMapper {
    //插入短链
    @Insert("INSERT INTO short_link (short_code, original_url) VALUES (#{shortCode}, #{originalUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ShortLink shortLink);
    //根据短链码查询短链
    @Select("SELECT * FROM short_link WHERE short_code = #{shortCode}")
    ShortLink findByShortCode(String shortCode);
    //更新短链访问次数
    @Update("UPDATE short_link SET access_count = access_count + 1 WHERE id = #{id}")
    void incrementAccessCount(Long id);
    //查询所有短链
    @Select("SELECT * FROM short_link ORDER BY created_at DESC")
    List<ShortLink> findAll();
    //删除短链
    @Delete("DELETE FROM short_link WHERE short_code = #{shortCode}")
    int deleteByShortCode(String shortCode);
}
