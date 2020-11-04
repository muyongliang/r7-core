package com.r7.core.assets.wallet.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: zs
 * @description: 钱包账单展示类型枚举
 * @date: 2020-10-30
 **/
public enum WalletBillIsShowEnum implements IEnum<Integer> {
    /**
     * 不展示
     */
    NO(1),
    /**
     * 展示
     */
    YES(2);

    private Integer isShow;

    WalletBillIsShowEnum(Integer isShow) {
        this.isShow = isShow;
    }

    @Override
    public Integer getValue() {
        return this.isShow;
    }
}
