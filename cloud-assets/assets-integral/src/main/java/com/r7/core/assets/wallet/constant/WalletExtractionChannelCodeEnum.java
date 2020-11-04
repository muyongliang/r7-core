package com.r7.core.assets.wallet.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: zs
 * @description: 钱包提现渠道标识类型枚举
 * @date: 2020-11-02
 **/
public enum WalletExtractionChannelCodeEnum implements IEnum<Integer> {
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
     * 其他
     */
    OTHER(4);

    private Integer channelCode;

    WalletExtractionChannelCodeEnum(Integer channelCode) {
        this.channelCode = channelCode;
    }

    @Override
    public Integer getValue() {
        return this.channelCode;
    }
}
