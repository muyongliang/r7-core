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
     * 认证返回异常
     */
    OAUTH_GET_TOKEN_FAIL_CLIENT_MISSING("oauth_get_token_fail_client_missing", "客户端信息缺失"),
    OAUTH_GET_TOKEN_FAIL_CLIENT("oauth_get_token_fail_client", "客户端认证失败"),
    OAUTH_GET_TOKEN_FAIL_USER("oauth_get_token_fail_user", "用户名或密码错误"),
    OAUTH_ACCESS_RESOURCE_INSUFFICIENT_AUTHORITY("oauth_access_resource_insufficient_authority", "资源权限不足"),
    OAUTH_ACCESS_RESOURCE_TOKEN_INVALID("oauth_access_resource_token_invalid", "登录凭证已失效"),
    CLIENT_ID_OR_SECRET_ERROR("client_id_or_secret_error", "client_id 或client_secret 不正确"),

    /**
     * 认证
     */
    OAUTH_INFO_SAVE_ERROR("oauth_info_save_error", "认证信息保存失败"),
    USER_ID_IS_NOT_EXIST("USER_ID_IS_NOT_EXIST", "用户id不存在"),
    OAUTH_ORDER_SAVE_ERROR("oauth_order_save_error", "认证订单创建失败"),
    OAUTH_ORDER_ID_IS_NULL("oauth_order_id_is_null", "认证订单id不能为空"),
    OAUTH_ORDER_IS_NOT_EXISTS("oauth_order_is_not_exists", "认证订单不存在"),
    OAUTH_ORDER_UPDATE_ERROR("oauth_order_update_error", "认证订单修改失败"),
    OAUTH_SAVE_ERROR("oauth_save_error", "用户认证保存失败"),
    OAUTH_USER_ID_IS_Not_EXISTS("oauth_user_id_is_not_exists", "认证用户id不存在"),
    OAUTH_USER_ID_IS_NULL("oauth_user_id_is_null", "认证用户id不能为null"),
    OAUTH_INFO_USER_ID_LENGTH_INCORRECT("oauth_info_user_id_length_is_incorrect", "认证用户id长度不正确"),
    OAUTH_INFO_ID_LENGTH_IS_INCORRECT("oauth_info_id_length_is_incorrect", "认证信息id长度不正确"),
    OAUTH_ORDER_USER_ID_LENGTH_INCORRECT("oauth_order_user_id_length_is_incorrect", "认证订单用户id长度不正确"),
    OAUTH_ORDER_ID_LENGTH_INCORRECT("oauth_order_id_length_is_incorrect", "认证订单id长度不正确"),
    OAUTH_SERVICE_USER_ID_LENGTH_INCORRECT("oauth_service_user_id_length_is_incorrect", "认证服务用户id长度不正确"),

    /**
     * 冻结
     */
    CHILL_SAVE_ERROR("chill_save_error", "冻结保存失败"),
    CHILL_ID_IS_NOT_EXISTS("chill_id_is_not_exists", "冻结不存在"),
    CHILL_USER_ID_IS_NULL("chill_user_id_is_null", "冻结用户id不能为空"),
    CHILL_USER_ID_LENGTH_INCORRECT("chill_user_id_length_is_incorrect", "冻结用户id长度不正确"),
    CHILL_RESOURCE_ID_LENGTH_INCORRECT("chill_resource_id_length_is_incorrect", "冻结资源id长度不正确"),

    /**
     * 用户
     */
    USER_IS_NOT_EXISTS("user_id_not_exists", "用户不存在。"),
    USER_CODE_IS_NOT_EXISTS("user_code_is_not_exists", "用户邀请码不存在"),
    USER_CODE_IS_EXISTS("user_code_is_exists", "用户邀请码已存在"),
    USER_ID_IS_NULL("user_id_is_null", "用户ID不能为空。"),
    USER_UPDATE_ERROR("user_update_error", "用户修改失败。"),
    USER_DELETE_ERROR("user_delete_error", "用户删除失败。"),
    USER_SAVE_ERROR("user_save_error", "用户注册失败。"),
    USER_PHONE_EXISTS("user_phone_exists", "电话号已经存在。"),
    USER_PHONE_ERROR("user_phone_error", "请填写正确的手机号。"),
    USER_LOGIN_NAME_ERROR("user_login_name_error", "用户名或密码错误。"),
    USER_SIGN_UP_SMS_SEND_ERROR("user_sign_up_sms_send_error", "请一分钟后再次发送验证码。"),
    USER_SIGN_UP_SMS_CODE_ERROR("user_sign_up_sms_code_error", "验证码错误或已失效，请重新发送验证码。"),
    USER_ID_LENGTH_IS_INCORRECT("user_id_length_is_incorrect", "用户id长度不正确"),

    /**
     * 用户角色
     */
    USER_ROLE_IS_EXISTS("user_role_is_exists", "用户角色已绑定"),
    USER_ROLE_IS_NOT_EXISTS("user_role_is_not_exists", "用户角色不存在"),
    USER_ROLE_BIND_ERROR("user_role_bind_error", "用户角色绑定失败"),
    USER_ROLE_UNBIND_ERROR("user_role_unbind_error", "用户角色解绑失败"),

    /**
     * 角色资源
     */
    ROLE_RESOURCE_IS_EXISTS("role_resource_is_exists", "角色资源已绑定。"),
    ROLE_RESOURCE_IS_NOT_EXISTS("role_resource_is_not_exists", "角色资源未绑定。"),
    ROLE_RESOURCE_BIND_ERROR("role_resource_bind_error", "角色资源绑定失败。"),
    ROLE_RESOURCE_UNBIND_ERROR("role_resource_unbind_error", "角色资源解绑失败。"),

    /**
     * 资源错误
     */
    RESOURCE_SUB_IS_EXISTS("resource_sub_is_exists", "当前资源存在子资源。"),
    RESOURCE_IS_NOT_EXISTS("resource_is_not_exists", "资源不存在。"),
    RESOURCE_ID_IS_NULL("resource_id_is_null", "资源ID不能为空。"),
    RESOURCE_SAVE_ERROR("resource_save_error", "资源新增失败。"),
    RESOURCE_UPDATE_ERROR("resource_update_error", "资源修改失败。"),
    RESOURCE_DELETE_ERROR("resource_delete_error", "资源删除失败。"),

    /**
     * 组织错误
     */
    ORGAN_CODE_IS_EXISTS("organ_code_is_exists", "组织编码已存在。"),
    ORGAN_NAME_IS_EXISTS("organ_name_is_exists", "组织名称已存在。"),
    ORGAN_SUB_IS_EXISTS("organ_sub_is_exists", "当前组织存在子组织。"),
    ORGAN_IS_NOT_EXISTS("organ_is_not_exists", "组织不存在。"),
    ORGAN_ID_IS_NULL("organ_id_is_null", "组织ID不能为空。"),
    ORGAN_PID_IS_NULL("organ_pid_is_null", "组织父ID不能为空。"),
    ORGAN_SAVE_ERROR("organ_save_error", "组织新增失败。"),
    ORGAN_UPDATE_ERROR("organ_update_error", "组织修改失败。"),
    ORGAN_DELETE_ERROR("organ_delete_error", "组织删除失败。"),

    /**
     * 角色错误
     */
    ROLE_NAME_IS_NULL("role_name_is_null", "角色名称不能为空。"),
    ROLE_NAME_IS_EXISTS("role_name_is_exists", "角色名已存在。"),
    ROLE_CODE_IS_NULL("role_code_is_null", "角色编码不能为空。"),
    ROLE_CODE_IS_EXISTS("role_code_is_exists", "角色编码已存在。"),
    ROLE_IS_NOT_EXISTS("role_is_not_exists", "角色不存在。"),
    ROLE_ID_IS_NULL("role_is_not_exists", "角色ID不能为空。"),
    ROLE_SAVE_ERROR("role_save_error", "角色新增失败。"),
    ROLE_UPDATE_ERROR("role_update_error", "角色修改失败。"),
    ROLE_DELETE_ERROR("role_delete_error", "角色删除失败。"),
    ROLE_ID_LENGTH_IS_INCORRECT("role_id_length_is_incorrect", "角色id长度不正确");

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
