package com.r7.core.assets.funds.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: zs
 * @description 支付渠道类型枚举
 * @date: 2020-10-30
 **/
public enum FundsChannelEnum implements IEnum<Integer> {
    /**
     * 支付宝
     */
    ALIPAY(1),
    /**
     * 微信
     */
    WECHAT(2),
    /**
     * 其他
     */
    OTHER(3);

    private Integer channel;

    FundsChannelEnum(Integer channel) {
        this.channel = channel;
    }

    @Override
    public Integer getValue() {
        return this.channel;
    }
}
