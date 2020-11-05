package com.r7.core.assets.wallet.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: zs
 * @description: 钱包账单交易类型枚举
 * @date: 2020-11-02
 **/
public enum WalletBillTypeEnum implements IEnum<Integer> {
    /**
     * 收入
     */
    INCOME(1),
    /**
     * 支出
     */
    EXPENSES(2),
    /**
     * 冻结
     */
    CHILL(3),
    /**
     * 解冻
     */
    MELTING(4);

    private Integer type;

    WalletBillTypeEnum(Integer type) {
        this.type = type;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }
}
