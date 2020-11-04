package com.r7.core.integral.constant;

import com.r7.core.common.constant.IError;

/**
 * @author wt
 * @Description 积分详情错误
 */
public enum CoreIntegralDetailEnum implements IError {
    /**
     * 积分详情错误
     */
    CORE_INTEGRAL_DETAIL_SAVE_ERROR("core_integral_detail_save_error","积分详情新增失败"),
    CORE_INTEGRAL_DETAIL_ID_IS_NOT_NULL("core_integral_detail_id_is_not_null","积分详情ID不能为空"),
    CORE_INTEGRAL_DETAIL_IS_NOT_EXISTS("core_integral_detail_is_not_exists","积分详情不存在")
    ;
    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    CoreIntegralDetailEnum(String code, String message) {
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
