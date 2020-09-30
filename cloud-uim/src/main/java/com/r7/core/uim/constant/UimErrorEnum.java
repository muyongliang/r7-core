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
     * 资源错误
     */
    RESOURCE_SUB_IS_NOT_EXISTS("resource_sub_is_not_exists", "当前资源存在子级。"),
    RESOURCE_IS_NOT_EXISTS("resource_is_not_exists", "资源不存在。"),
    RESOURCE_ID_IS_NULL("resource_id_is_null", "资源ID不能为空。"),
    RESOURCE_SAVE_ERROR("resource_save_error", "资源新增失败。"),
    RESOURCE_UPDATE_ERROR("resource_update_error", "资源修改失败。"),
    RESOURCE_DELETE_ERROR("resource_delete_error", "资源删除失败。"),

    /**
     * 角色错误
     */
    ROLE_NAME_IS_NULL("role_name_is_null", "角色名称不能为空。"),
    ROLE_NAME_IS_EXISTS("role_name_is_exists", "角色名已存在。"),
    ROLE_CODE_IS_NULL("role_code_is_null", "角色编码不能为空。"),
    ROLE_CODE_IS_EXISTS("role_code_is_exists", "角色编码已存在。"),
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
