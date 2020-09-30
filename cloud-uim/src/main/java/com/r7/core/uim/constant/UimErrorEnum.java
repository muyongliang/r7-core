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
    ROLE_DELETE_ERROR("role_delete_error", "角色删除失败。");

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
