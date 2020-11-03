package com.r7.core.assets.funds.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: zs
 * @description 支付状态类型枚举
 * @date: 2020-10-30
 **/
public enum FundsStatusEnum implements IEnum<Integer> {
    /**
     * 交易成功
     */
    SUCCESS(1),
    /**
     * 交易失败
     */
    FAIL(2),
    /**
     * 待支付
     */
    WAIT(3),
    /**
     * 取消支付
     */
    CANCEL(4);

    private Integer status;

    FundsStatusEnum(Integer status) {
        this.status = status;
    }

    @Override
    public Integer getValue() {
        return this.status;
    }
}
