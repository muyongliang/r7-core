package com.r7.core.uim.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author wt
 * @Description 系统用户删除
 */
public enum UimSysUserDelEnum implements IEnum<Integer> {

    /**
     *未删除
     */
    NOTDEL(1),

    /**
     * 删除
     */
    DEL(2)
    ;
    private Integer del;

    UimSysUserDelEnum(Integer del){
        this.del = del;
    }


    @Override
    public Integer getValue() {
        return this.del;
    }
}
