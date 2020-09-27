package com.r7.core.uim.constant;

import com.r7.core.common.constant.IError;

/**
 * 统一登录授权错误
 *
 * @author zhongpingli
 * @date 2020-09-25
 */
public enum UimErrorEnum implements IError {

    /**
     * 角色不存在
     */
    ROLE_IS_NOT_EXISTS("role_is_not_exists", "角色不存在。"),
    ROLE_ID_IS_NULL("role_is_not_exists", "角色ID不能为空。");

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    UimErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
