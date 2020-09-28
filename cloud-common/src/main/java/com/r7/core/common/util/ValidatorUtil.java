package com.r7.core.common.util;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @Author muyongliang
 * @Date 2020/9/28 10:40
 * @Description 实体类校验工具类
 */
public class ValidatorUtil {
    private static ValidatorUtil validatorUtil = new ValidatorUtil();
    private static Validator validator;

    private ValidatorUtil() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    public static <T> Set<ConstraintViolation<T>> validate(T object) {
        Set<ConstraintViolation<T>> violationSet = validator.validate(object);
        return violationSet;
    }
}
