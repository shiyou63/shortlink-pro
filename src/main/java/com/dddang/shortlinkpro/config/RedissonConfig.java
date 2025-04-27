package com.dddang.shortlinkpro.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: Redisson配置，用于连接Redis数据库，功能包括连接池管理、连接超时、命令执行超时、命令重试等。
 * @Author : dddang
 * @Date :2025-04-22  下午3:26
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Value("${spring.redis.password:}") // 冒号表示密码可为空
    private String redisPassword;

    @Value("${spring.redis.database:0}") // 默认使用0号数据库
    private int redisDatabase;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();

        config.useSingleServer()
                .setAddress("redis://" + redisHost + ":" + redisPort)
                .setPassword(redisPassword.isEmpty() ? null : redisPassword)
                .setDatabase(redisDatabase)
                .setConnectionPoolSize(64)      // 连接池最大连接数
                .setConnectionMinimumIdleSize(10) // 最小空闲连接
                .setConnectTimeout(5000)        // 连接超时5秒
                .setTimeout(3000)               // 命令执行超时3秒
                .setRetryAttempts(3)            // 命令重试次数
                .setRetryInterval(1500);        // 重试间隔1.5秒

        return Redisson.create(config);
    }
}

