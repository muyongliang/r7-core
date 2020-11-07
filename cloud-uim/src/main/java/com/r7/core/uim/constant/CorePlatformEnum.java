package com.r7.core.uim.constant;

import com.r7.core.common.constant.IError;

/**
 * @author wt
 * @Description 平台信息错误
 */
public enum CorePlatformEnum implements IError {
    /**
     * 平台信息错误
     */
    CORE_PLATFORM_SAVE_ERROR("core_platform_save_error","平台信息新增失败"),
    CORE_PLATFORM_APP_NAME_UPDATE_ERROR("core_platform_app_name_update_error","平台名称修改失败"),
    CORE_PLATFORM_DESCRIPTION_UPDATE_ERROR("core_platform_description_update_error","平台描述修改失败"),
    CORE_PLATFORM_ID_IS_NOT_NULL("core_platform_id_is_not_null","平台id不能为空"),
    CORE_PLATFORM_APP_NAME_IS_NOT_NULL("core_platform_app_name_is_not_null","平台名称不能为空"),
    CORE_PLATFORM_IS_NOT_EXISTS("core_platform_is_not_exists","平台信息不存在"),
    CORE_PLATFORM_APP_NAME_IS_EXISTS("core_platform_app_name_is_exists","平台名称已经存在"),
    CORE_PLATFORM_ID_LENGTH_ERROR("core_platform_id_length_error","平台id长度必须是19位")
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    CorePlatformEnum(String code, String message) {
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
