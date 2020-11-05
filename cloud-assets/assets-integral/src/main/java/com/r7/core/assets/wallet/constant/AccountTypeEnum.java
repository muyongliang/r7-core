package com.r7.core.assets.wallet.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: zs
 * @description 账户类型枚举类
 * @date: 2020-10-30
 **/
public enum AccountTypeEnum implements IEnum<Integer> {
    /**
     * 提现账户
     */
    WITHDRAW(1),
    /**
     * 转账
     */
    TRANSFER(2),
    /**
     * 其他
     */
    OTHER(3);
    private Integer type;

    AccountTypeEnum(Integer type) {
        this.type = type;
    }

    @Override
    public Integer getValue() {
        return this.type;
    }
}
