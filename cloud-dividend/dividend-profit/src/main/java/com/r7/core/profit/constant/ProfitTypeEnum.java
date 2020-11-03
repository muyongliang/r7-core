package com.r7.core.profit.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author wt
 * @Description 分润类型
 */
public enum ProfitTypeEnum implements IEnum<Integer> {

    /**
     * 权益商品
     */
    EQUITYGOODS(1),
    /**
     * 游戏分润
     */
    GAMEPROFIT(2)
;

    private  Integer type;

    ProfitTypeEnum(Integer type){
        this.type = type;
    }


    @Override
    public Integer getValue() {
        return this.type;
    }
}
