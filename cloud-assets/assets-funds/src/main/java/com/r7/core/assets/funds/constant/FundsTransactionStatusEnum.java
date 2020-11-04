package com.r7.core.assets.funds.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: zs
 * @description
 * @date: 2020-10-30
 **/
public enum FundsTransactionStatusEnum implements IEnum<Integer> {
    /**
     * 待支付
     */
    WAIT(3),
    /**
     * 已支付
     */
    SUCCESS(1),
    /**
     * 支付失败
     */
    FAIL(2);

    private Integer transactionStatus;

    FundsTransactionStatusEnum(Integer transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Override
    public Integer getValue() {
        return this.transactionStatus;
    }
}
