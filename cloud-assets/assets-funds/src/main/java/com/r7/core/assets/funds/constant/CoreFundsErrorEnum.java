package com.r7.core.assets.funds.constant;

import com.r7.core.common.constant.IError;

/**
 * @author zs
 * @description: 核心资金模块异常错误
 * @date : 2020-10-28
 */
public enum CoreFundsErrorEnum implements IError {

    /**
     * 资金
     */
    FUNDS_SAVE_ERROR("funds_save_error", "资金记录保存失败"),
    FUNDS_ID_IS_NOT_EXISTS("funds_id_is_not_exists", "资金记录id不存在"),
    FUNDS_UPDATE_ERROR("funds_update_error", "资金修改失败"),
    FUNDS_IS_NOT_EXISTS("FUNDS_IS_NOT_EXISTS", "资金记录不存在"),
    FUNDS_USER_ID_IS_NOT_EXISTS("FUNDS_USER_ID_IS_NOT_EXISTS", "资金用户id不存在"),
    FUNDS_ID_IS_NULL("funds_id_is_null", "资金id不能为空"),
    FUNDS_USER_ID_IS_NULL("funds_user_id_is_null", "资金用户id不能为空"),
    FUNDS_USER_ID_LENGTH_INCORRECT("funds_user_id_length_is_incorrect", "资金用户id长度不正确"),
    FUNDS_ID_LENGTH_IS_INCORRECT("funds_id_length_is_incorrect", "资金id长度不正确"),
    FUNDS_IN_ORDER_SN_LENGTH_INCORRECT("funds_in_order_sn_length_incorrect", "资金内部订单长度不正确"),
    FUNDS_OUT_ORDER_SN_LENGTH_INCORRECT("funds_out_order_sn_length_incorrect", "资金外部订单长度不正确");

    /**
     * 错误码
     */
    private String code;
    /**
     * 异常消息
     */
    private String message;

    CoreFundsErrorEnum(String code, String message) {
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
