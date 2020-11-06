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
    USER_SYS_USER_NAME_IS_NOT_NULL("user_sys_user_name_is_not_null","系统用户昵称不能为空"),
    USER_SYS_ID_IS_NOT_NULL("user_sys_id_is_not_null","系统用户id不能为空"),
    USER_SYS_UPDATE_ERROR("user_sys_update_error","系统用户信息修改失败"),
    USER_SYS_IS_NOT_EXISTS("user_sys_is_not_exists","该系统用户不存在"),
    USER_SYS_LOGIN_NAME_IS_EXISTS("user_sys_login_name_is_exists","用户登录名已存在"),
    USER_SYS_PHONE_IS_EXISTS("user_sys_phone_is_exists","电话已存在"),
    USER_SYS_EMAIL_IS_EXISTS("user_sys_email_is_exists","邮箱已存在"),
    USER_SYS_USER_NAME_IS_EXISTS("user_sys_user_name_is_exists","昵称已存在"),
    USER_SYS_UPDATE_STATUS_ERROR("user_sys_update_status_error","系统用户的状态修改失败"),
     USER_SYS_PHONE_NUMBER_IS_NOT_NULL("user_sys_phone_number_is_not_null","电话不能为空"),
     USER_SYS_EMAIL_ID_NOT_NULL("user_sys_email_is_not_null","邮箱不能为空"),
    USER_SYS_CODE_IS_NOT_NULL("user_sys_code_is_not_null","邀请码不能为空"),
    USER_SYS_CODE_IS_EXISTS("user_sys_code_is_exists","系统用户邀请码已存在"),
    USER_SYS_CODE_IS_NOT_EXISTS("user_sys_code_is_not_exists","邀请码不存在"),
    USER_SYS_PASSWORD_UPDATE_ERROR("user_sys_password_update_error","系统用户密码修改失败"),
    USER_SYS_BRANCH_ID_LENGTH_ERROR("user_sys_branch_id_length_error","部门id长度必须是19位"),
    USER_SYS_BRANCH_IS_NOT_EXISTS("user_sys_branch_is_not_exists","部门不存在"),
    USER_SYS_ID_LENGTH_ERROR("user_sys_id_length_error","用户id长度必须是19位"),
    USER_SYS_LENGTH_ERROR("user_sys_length_error","长度必须是19位"),
    USER_SYS_PASSWORD_ERRORUIM_SYS_USER_ENUM("user_sys_password_error","密码错误")
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
