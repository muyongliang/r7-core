package com.r7.core.assets.wallet.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: zs
 * @description: 钱包提现明细提现状态类型枚举
 * @date: 2020-11-02
 **/
public enum WalletExtractionExtractionStatusEnum implements IEnum<Integer> {
    /**
     * 未到账
     */
    NOT_ARRIVED(1),
    /**
     * 已到账
     */
    HAS_ARRIVED(2),
    /**
     * 提现失败
     */
    WITHDRAWAL_FAILED(3);

    private Integer extractionStatus;

    WalletExtractionExtractionStatusEnum(Integer extractionStatus) {
        this.extractionStatus = extractionStatus;
    }

    @Override
    public Integer getValue() {
        return this.extractionStatus;
    }
}
