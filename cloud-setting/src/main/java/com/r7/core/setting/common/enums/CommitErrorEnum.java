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
    BAD_REQUEST("400", "参数校验失败"),
    UNAUTHORIZED("401", "用户未认证"),
    FORBIDDEN("403", "用户无权限"),
    NOT_FOUND("404", "资源不存在"),
    SERVER_ERROR("500", "服务器发生未知错误"),
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
