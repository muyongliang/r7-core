package com.r7.core.uim.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author wt
 * @Description 系统用户状态
 */
public enum UimSysUserStatusEnum implements IEnum<Integer> {

        /**
         * 正常
         */
    NORMALSTATUS(1),

        /**
         * 冻结
         */
    FREEZESTATUS_ENUM(2);

    private Integer status;

    UimSysUserStatusEnum(Integer status){
        this.status = status;
    }


    @Override
    public Integer getValue() {
        return this.status;
    }
}
