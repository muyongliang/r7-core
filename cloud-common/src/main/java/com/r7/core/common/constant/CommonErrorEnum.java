package com.r7.core.common.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * @author muyongliang
 * @date 2020/10/29 10:20
 * @description 公共异常类，使用相同异常code，可以自定义的异常信息
 */
public enum CommonErrorEnum implements IError {
    /**
     * 异常
     */
    ILLEGAL_ARGUMENT("非法参数");
    /**
     * 错误信息
     */
    private String message;

    CommonErrorEnum(String message) {
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public CommonErrorEnum setMessage(String message) {
        if (StringUtils.isNotBlank(message)) {
            this.message = message;
        }
        return this;
    }
}
