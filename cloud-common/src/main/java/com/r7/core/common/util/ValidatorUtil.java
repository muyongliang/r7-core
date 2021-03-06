package com.r7.core.common.util;


import cn.hutool.core.lang.Validator;

/**
 * 公共验证工具类
 *
 * @author zhongpingli
 */
public class ValidatorUtil {


    public static boolean validatorPhoneNumber(Long phoneNumber) {
        return Validator.isMatchRegex("^1[34578]\\d{9}$", phoneNumber.toString());
    }

    public static boolean validatorEmail(String email) {
        return Validator.isEmail(email);
    }
}
