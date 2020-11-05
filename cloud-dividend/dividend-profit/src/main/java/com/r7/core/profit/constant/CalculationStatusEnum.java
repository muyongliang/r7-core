package com.r7.core.profit.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author wt
 * @Description 分润明细的计算状态（即核算状态）
 */
public enum CalculationStatusEnum implements IEnum<Integer> {

    /**
     * 未计算(未核算)
     */
    NOTCALCULATED(1),

    /**
     * 已计算(已经核算)
     */
    CALCULATED(2);

    private Integer status;

    CalculationStatusEnum(Integer status){
        this.status = status;
    }

    @Override
    public Integer getValue() {
        return this.status;
    }
}
