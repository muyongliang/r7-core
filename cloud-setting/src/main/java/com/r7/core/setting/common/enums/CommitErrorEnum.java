package com.r7.core.setting.common.enums;

import com.r7.core.common.constant.IError;

/**
 * 公共异常
 *
 * @author mrli
 */
public enum CommitErrorEnum implements IError {
    /**
     * 异常
     */
    UNVALID_API("unvalid_api", "没有找到对应的API"),
    ARGUMENT_NOT_VALID("valid_failed", "参数校验失败"),
    UNKNOWN_ERROR("unknown_error", "未知错误"),
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    CommitErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
