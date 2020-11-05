package com.r7.core.assets.wallet.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: zs
 * @description: 钱包提现状态审核状态类型枚举
 * @date: 2020-11-02
 **/
public enum WalletExtractionStatusEnum implements IEnum<Integer> {
    /**
     * 待审核
     */
    WAIT(1),
    /**
     * 进行中
     */
    PROCESSING(2),
    /**
     * 审核通过
     */
    PASSING(3),
    /**
     * 审核驳回
     */
    DISALLOWANCE(4);

    private Integer status;

    WalletExtractionStatusEnum(Integer status) {
        this.status = status;
    }

    @Override
    public Integer getValue() {
        return this.status;
    }
}
