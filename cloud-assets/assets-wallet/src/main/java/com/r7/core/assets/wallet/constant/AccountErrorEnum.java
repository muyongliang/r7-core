package com.r7.core.assets.wallet.constant;

import com.r7.core.common.constant.IError;

/**
 * @author zs
 * @description: 核心账户错误异常
 * @date : 2020-10-28
 */
public enum AccountErrorEnum implements IError {

    /**
     * 用户账户
     */
    ACCOUNT_SAVE_ERROR("account_save_error", "用户账户新增失败"),
    USER_ID_IS_NOT_EXISTS("user_id_is_not_exists", "用户id不存在"),
    ACCOUNT_USER_ID_IS_NULL("account_user_id_is_null", "用户id不能为空");

    /**
     * 错误码
     */
    private String code;
    /**
     * 异常消息
     */
    private String message;

    AccountErrorEnum(String code, String message) {
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
