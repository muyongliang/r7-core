package com.r7.core.assets.wallet.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: zs
 * @description: 钱包提现异常处理方式类型枚举类型
 * @date: 2020-11-02
 **/
public enum WalletExtractionErrorDealWayEnum implements IEnum<Integer> {
    /**
     * 已退回
     */
    RETURNED(1),
    /**
     * 已重提
     */
    Reclaimed(2),
    /**
     * 异常
     */
    ABNORMAL(3),
    /**
     * 正常
     */
    NORMAL(4);
    private Integer errorDealWay;

    WalletExtractionErrorDealWayEnum(Integer errorDealWay) {
        this.errorDealWay = errorDealWay;
    }

    @Override
    public Integer getValue() {
        return errorDealWay;
    }
}
