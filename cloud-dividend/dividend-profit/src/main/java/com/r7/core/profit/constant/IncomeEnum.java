package com.r7.core.profit.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author wt
 * @Description 发放状态
 */
public enum IncomeEnum implements IEnum<Integer> {
    /**
     * 已发放
     */

    ISSUED(2),

    /**
     * 未发放
     */
    NOTISSUED(1);

    private  Integer status;


    IncomeEnum(Integer status){
        this.status = status;
    }

    @Override
    public Integer getValue() {
        return this.status;
    }

    }
