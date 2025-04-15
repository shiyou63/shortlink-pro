package com.dddang.shortlinkpro.mapper;

import com.dddang.shortlinkpro.model.entity.ShortLink;
import org.apache.ibatis.annotations.*;

import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> 5cdac6da93f666c791bf28adb2e7b088aaebfee5

/**
 * @author dddang
 * @create 2025-04-13  下午2:52
 */
@Mapper
public interface ShortLinkMapper {
<<<<<<< HEAD

    // 插入短链（自动生成ID）
    @Insert("INSERT INTO short_link (short_code, original_url, created_at, access_count) " +
            "VALUES (#{shortCode}, #{originalUrl}, #{createdAt}, #{accessCount})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ShortLink shortLink);

    // 根据短码查询（避免空指针）
    @Select("SELECT * FROM short_link WHERE short_code = #{shortCode}")
    Optional<ShortLink> findByShortCode(String shortCode);

    // 检查短码是否存在（用于防止重复）
    @Select("SELECT COUNT(*) FROM short_link WHERE short_code = #{shortCode}")
    boolean existsByShortCode(String shortCode);

    // 增加访问次数（原子操作）
    @Update("UPDATE short_link SET access_count = access_count + 1 WHERE id = #{id}")
    void incrementAccessCount(Long id);

    // 获取全部短链（简单实现）
    @Select("SELECT * FROM short_link ORDER BY created_at DESC")
    List<ShortLink> findAll();

    // 删除短链（返回是否成功）
    @Delete("DELETE FROM short_link WHERE short_code = #{shortCode}")
    int deleteByShortCode(String shortCode);
}
=======
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
>>>>>>> 5cdac6da93f666c791bf28adb2e7b088aaebfee5
