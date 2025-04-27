package com.dddang.shortlinkpro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 配置CORS跨域支持
 * @Author dddang
 * @Date 2025-04-14  下午6:37
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 处理视图跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 根路径重定向到前端页面
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/manage").setViewName("forward:/manage.html");
    }

    // 关键：显式声明静态资源路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8080") // 前端实际端口
                .allowedMethods("*");
    }
}
