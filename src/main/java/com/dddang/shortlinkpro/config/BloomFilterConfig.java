package com.dddang.shortlinkpro.config;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

/**
 * @author 17470
 */
@Configuration
public class BloomFilterConfig {
    /**
     * 基础版字符串过滤器
     * @param expectedInsertions 预期数据量（建议设置为业务峰值的1.2倍）
     * @param fpp 可接受误判率（范围：0 < fpp < 1）
     */
    @Bean
    public BloomFilter<String> stringBloomFilter(
            @Value("${bloom.filter.expected-insertions:1000000}") long expectedInsertions,
            @Value("${bloom.filter.fpp:0.03}") double fpp) {
        return BloomFilter.create(
                Funnels.stringFunnel(Charset.forName("UTF-8")),
                expectedInsertions,
                fpp
        );
    }

    /**
     * 数字型过滤器（如ID校验）
     */
    @Bean
    public BloomFilter<Long> longBloomFilter() {
        return BloomFilter.create(
                Funnels.longFunnel(),
                500_000,  // 预期元素量
                0.01      // 误判率
        );
    }
}
