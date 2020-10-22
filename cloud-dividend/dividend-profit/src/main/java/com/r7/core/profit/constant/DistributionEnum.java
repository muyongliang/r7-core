package com.r7.core.profit.constant;

import com.r7.core.common.constant.IError;

/**
 * @author wt
 * @Descriptionc 发放错误
 */
public enum DistributionEnum  implements IError {

    /**
     * 发放错误
     */
    DISTRIBUTION_ERROR("distribution_error","金额和积分不能同时发放")



    ;
    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    DistributionEnum(String code, String message) {
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
