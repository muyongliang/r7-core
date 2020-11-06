package com.r7.core.proxy.constant;

import com.r7.core.common.constant.IError;

/**
 * @author wutao
 * @Description 代理层级错误
 *
 */
public enum CoreProxyEnum implements IError {
    /**
     * 层级错误
     */
    CORE_PROXY_SAVE_ERROR("core_proxy_save_error","层级信息新增失败"),
    CORE_PROXY_USER_PID_IS_NULL("core_proxy_user_pid_is_null","用户的父id不能为空"),
    CORE_PROXY_USER_ID_IS_NULL("core_proxy_user_id_is_null","用户id不能为空"),
    CORE_PROXY_USER_ORGAN_ID_IS_NULL("core_proxy_user_organ_id_is_null","用户的组织id不能为空"),
    CORE_PROXY_ID_IS_NOT_EXISTS("core_proxy_id_is_not_exists","代理层级不存在"),
    CORE_PROXY_UPDATE_SUBORDINATE_ERROR("core_proxy_update_subordinateNum_error","用户下级人数修改失败"),
    CORE_PROXY_UPDATE_ERROR("core_proxy_update_error","层级信息修改失败"),
    CORE_PROXY_ID_IS_NOT_NULL("core_proxy_id_is_not_ull","层级id不能为空"),
    CORE_PROXY_UPDATE_PID_ERROR("core_proxy_update_pid_error","层级的父id修改失败"),
    CORE_PROXY_UPDATE_LEVEL_ERROR("core_proxy_update_level_error","层级值修改失败"),
    CORE_PROXY_SUB_IS_EXISTS("core_proxy_sub_is_exists",
            "不能移动到自己的下级下面"),
    CORE_ID_LENGTH_ERROR("core_id_length_error","id长度必须是19位")
    ;


    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    CoreProxyEnum(String code, String message) {
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

