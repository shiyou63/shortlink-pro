package com.dddang.shortlinkpro.service;

<<<<<<< HEAD
import com.dddang.shortlinkpro.model.dto.LinkDetail;
import com.dddang.shortlinkpro.model.entity.ShortLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
=======
import com.dddang.shortlinkpro.model.entity.ShortLink;
>>>>>>> 5cdac6da93f666c791bf28adb2e7b088aaebfee5

import java.util.List;

/**
 * @author dddang
 * @create 2025-04-13  下午2:56
 */
public interface ShortLinkService {
<<<<<<< HEAD

    String createShortLink(String originalUrl);
    String getOriginalUrl(String shortCode);
    List<ShortLink> getAllShortLinks();
    boolean deleteByShortCode(String shortCode);
}

=======
    // 创建短链
     String createShortLink(String originalUrl);
     // 根据短链获取原始URL
     String getOriginalUrl(String shortCode);
     //显示全部短链
     List<ShortLink> getAllShortLinks();
 public boolean deleteByShortCode(String shortCode);

}
>>>>>>> 5cdac6da93f666c791bf28adb2e7b088aaebfee5
