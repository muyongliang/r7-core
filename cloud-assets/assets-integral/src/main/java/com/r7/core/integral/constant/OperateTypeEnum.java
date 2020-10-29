package com.r7.core.integral.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author wt
 * @Description 积分枚举
 */
public enum OperateTypeEnum implements IEnum<Integer> {

    /**
     * 积分增加
     */
    ADD(1),
    /**
     * 积分减少
     */
    REDUCE(2)
    ;


    private  Integer operateType;

    OperateTypeEnum(Integer operateType){
        this.operateType = operateType;
    }

    @Override
    public Integer getValue() {
        return this.operateType;
    }


}
