package com.r7.core.profit.constant;

import com.r7.core.common.constant.IError;

/**
 * @author wt
 * @Description 核算错误
 */
public enum SettlementEnum implements IError {

    /**
     * 核算错误
     */
    SETTLEMENT_ERROR("settlement_error","金额和积分不能同时核算"),
    SETTLEMENT_IS_NOT_EXISTS("settlement_is_not_exists","指定的时间之前没有要核算的分润明细")
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    SettlementEnum(String code, String message) {
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
