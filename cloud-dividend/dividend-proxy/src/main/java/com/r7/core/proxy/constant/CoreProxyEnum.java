package com.r7.core.proxy.constant;

import com.r7.core.common.constant.IError;

/**
 * @author wutao
 * @Description 代理层级错误
 * @date 2020/10/9
 */
public enum CoreProxyEnum implements IError {

    CORE_PROXY_SAVE_ERROR("core_proxy_save_error","层级信息新增失败"),
    CORE_PROXY_USER_PID_ERROR("core_proxy_user_pid_error","用户的父id不能为空"),
    CORE_PROXY_USER_ID_ERROR("core_proxy_user_id_error","用户id不能为空"),
    CORE_PROXY_USER_ORGAN_ID_ERROR("core_proxy_user_organ_id_error","用户的组织id不能为空"),
    CORE_PROXY_ID_ERROR("core_proxy_id_error","代理层级id不能为空");
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

