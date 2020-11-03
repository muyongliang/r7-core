package com.r7.core.assets.wallet.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: zs
 * @description: 账户渠道类型枚举
 * @date: 2020-10-30
 **/
public enum AccountChannelEnum implements IEnum<Integer> {
    /**
     * 支付宝
     */
    ALIPAY(1),
    /**
     * 微信
     */
    WECHAT(2),
    /**
     * 银联
     */
    UNIONPAY(3),
    /**
     * 股票
     */
    STOCK(4),
    /**
     * 其他
     */
    OTHER(5);

    private Integer channel;

    AccountChannelEnum(Integer channel) {
        this.channel = channel;
    }

    @Override
    public Integer getValue() {
        return this.channel;
    }
}
