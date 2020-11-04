package com.r7.core.assets.wallet.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: zs
 * @description:
 * @date: 2020-11-02
 **/
public enum WalletBillStatusEnum implements IEnum<Integer> {
    /**
     * 交易成功
     */
    SUCCESS(1),
    /**
     * 待支付
     */
    WAIT_PAYMENT(2),
    /**
     * 支付失败
     */
    FAIL(3);

    private Integer status;

    WalletBillStatusEnum(Integer status) {
        this.status = status;
    }

    @Override
    public Integer getValue() {
        return this.status;
    }
}
