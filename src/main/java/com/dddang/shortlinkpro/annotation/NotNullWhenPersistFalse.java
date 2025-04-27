package com.dddang.shortlinkpro.annotation;

import com.dddang.shortlinkpro.validator.NotNullWhenPersistFalseValidator;

import javax.validation.Constraint;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:
 * @Author : dddang
 * @Date :2025-04-26  下午3:53
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullWhenPersistFalseValidator.class)
public @interface NotNullWhenPersistFalse {
    String message() default "临时存储必须设置过期时间";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

