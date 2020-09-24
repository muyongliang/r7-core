package com.yym.setting.common.util;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by MuYongLiang on 2018/7/5.
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
