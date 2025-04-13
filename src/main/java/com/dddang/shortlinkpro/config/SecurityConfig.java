package com.dddang.shortlinkpro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author dddang
 * @create 2025-04-13  下午3:19
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index.html", "/static/**").permitAll() // 允许前端资源
                .antMatchers("/api/**").permitAll() // 开放所有API接口
                .anyRequest().denyAll() // 其他请求全部拒绝
                .and()
                .csrf().disable() // 禁用CSRF
                .formLogin().disable() // 禁用登录页
                .httpBasic().disable(); // 禁用HTTP Basic认证
    }
}
