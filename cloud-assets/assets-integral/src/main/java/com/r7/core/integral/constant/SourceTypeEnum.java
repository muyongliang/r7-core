package com.r7.core.integral.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author wt
 * @Description 积分来源类型枚举
 */
public enum SourceTypeEnum implements IEnum<Integer> {

    /**
     * 游戏
     */
    GAME(1),

    /**
     * 完成系统任务
     */
    TASK(2),

    /**
     * 权益转换积分
     */
    EXCHANGE(3),

    /**
     * 现金购买积分
     */
    BUY(4),

    /**
     * 用户注册成功赠送积分
     */
    PRESENTATION(5),

    /**
     * 权益商品卖出
     */
    SELL(6);


    private Integer sourceType;

    SourceTypeEnum(Integer sourceType) {
        this.sourceType = sourceType;
    }


    @Override
    public Integer getValue() {
        return this.sourceType;
    }

}
