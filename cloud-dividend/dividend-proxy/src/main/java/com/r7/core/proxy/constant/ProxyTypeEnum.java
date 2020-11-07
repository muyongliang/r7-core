package com.r7.core.proxy.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author wt
 * @Description 层级类型
 */
public enum ProxyTypeEnum implements IEnum<Integer> {

    /**
     *销售代
     */
    SALESPROXY(1),
    /**
     * 其他
     */
    OTHERTYPE(2);

    private Integer type;

    ProxyTypeEnum(Integer type){
        this.type = type;
    }


    @Override
    public Integer getValue() {
        return this.type;
    }
}
