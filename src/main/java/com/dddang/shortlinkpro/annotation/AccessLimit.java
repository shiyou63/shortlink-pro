package com.dddang.shortlinkpro.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 访问限制注解，要求一分钟内不超过10次生成短链
 * @Author: dddang
 * @Date: 2025-04-20  下午2:53
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    /**
     * 限制周期(秒)
     */
    int seconds()default 60;

    /**
     * 规定周期内限制次数
     */
    int maxCount()default 10;

    /**
     * 触发限制时的消息提示
     */
    String msg() default "操作频率过高";
}
