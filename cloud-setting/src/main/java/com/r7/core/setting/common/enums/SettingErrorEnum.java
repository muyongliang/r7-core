package com.r7.core.setting.common.enums;

import com.r7.core.common.constant.IError;

/**
 * 公共异常
 *
 * @author mrli
 */
public enum SettingErrorEnum implements IError {
    /**
     * 异常
     */
    SETTING_IS_NOT_EXISTS("setting_is_not_exists", "配置项不存在"),
    PAGE_SIZE_IS_NULL("page_size_is_null", "分页大小不能为空"),
    PAGE_NUM_IS_NULL("page_num_is_null", "分页页码不能为空"),
    SETTING_ID_IS_NULL("setting_id_is_null", "配置信息ID不能为空");

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    SettingErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
