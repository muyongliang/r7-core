package com.r7.core.integral.constant;

import com.r7.core.common.constant.IError;

/**
 * @author wt
 * @Description 当前用户积分错误
 */
public enum CoreIntegralEnum implements IError {
    /**
     * 当前用户积分信息错误
     */
    CORE_INTEGRAL_SAVE_ERROR("core_integral_save_error", "当前用户积分信息新增失败"),
    CORE_INTEGRAL_ID_IS_NOT_NULL("core_integral_id_is_not_null", "当前用户积分信息ID不能为空"),
    CORE_INTEGRAL_USER_ID_IS_NOT_NULL("core_integral_user_id_is_not_null", "当前用户ID不能为空"),
    CORE_INTEGRAL_IS_NOT_EXISTS("core_integral_is_not_exists", "当前用户积分信息不存在"),
    CORE_INTEGRAL_ADD_ERROR("core_integral_add_error", "用户积分增加失败"),
    CORE_INTEGRAL_REDUCE_ERROR("core_integral_reduce_error", "用户积分减少失败"),
    CORE_INTEGRAL_TOTAL_ERROR("core_integral_total_error", "用户总积分修改失败"),
    CORE_INTEGRAL_SIGN_ERROR("core_integral_sign_error", "用户的积分数据已被非法修改"),
    CORE_INTEGRAL_USER_INTEGRAL_IS_NOT_ENOUGH("core_integral_user_integral_is_not_enough",
            "用户积分不够使用");
    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    CoreIntegralEnum(String code, String message) {
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
