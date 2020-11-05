package com.r7.core.assets.wallet.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: zs
 * @description: 钱包提现明细提现进度状态类型枚举
 * @date: 2020-11-02
 **/
public enum WalletExtractionScheduleEnum implements IEnum<Integer> {
    /**
     * 提现审核中
     */
    WITHDRAWAL_REVIEW(1),
    /**
     * 提现中
     */
    WITHDRAWING(2),
    /**
     * 提现失败
     */
    WITHDRAWAL_FAILED(3),
    /**
     * 提现成功
     */
    SUCCESSFUL_WITHDRAWAL(4);

    private Integer schedule;

    WalletExtractionScheduleEnum(Integer schedule) {
        this.schedule = schedule;
    }

    @Override
    public Integer getValue() {
        return this.schedule;
    }
}
