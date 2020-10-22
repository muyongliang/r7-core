package com.r7.core.uim.constant;

import com.r7.core.common.constant.IError;

/**
 * @author wt
 * @Description 系统用户错误
 */
public enum UimSysUserEnum implements IError {


    /**
     * 系统用户错误
     */
    USER_EMAIL_ERROR("user_email_error","邮箱格式不正确"),
    USER_SYS_SAVE_ERROR("user_sys_save_error","系统用户添加失败"),
    USER_SYS_PHONE_IS_NOT_EXISTS("user_sys_phone_is_not_exists","电话不存在"),
    USER_SYS_EMAIL_IS_NOT_EXISTS("user_sys_email_is_not_exists","邮箱不存在")
    ;
    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    UimSysUserEnum(String code, String message) {
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
    }}
