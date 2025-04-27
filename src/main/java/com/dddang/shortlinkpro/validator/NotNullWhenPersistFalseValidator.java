package com.dddang.shortlinkpro.validator;

import com.dddang.shortlinkpro.annotation.NotNullWhenPersistFalse;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 临时存储必须设置过期时间校验器
 *
 * @author dddang
 */
public class NotNullWhenPersistFalseValidator
    implements ConstraintValidator<NotNullWhenPersistFalse, Object> {
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 需通过反射获取persist值，此处为简化示例
        // 实际实现需根据具体业务上下文判断
        return value != null;
    }
}