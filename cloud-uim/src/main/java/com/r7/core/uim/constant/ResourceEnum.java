package com.r7.core.uim.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 资源枚举
 *
 * @author zhongpingli
 */
public enum ResourceEnum implements IEnum<Integer> {
    /**
     * 菜单
     */
    MENU(1),
    /**
     * 按钮
     */
    BUTTON(2);

    private Integer code;

    ResourceEnum(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getValue() {
        return this.code;
    }
}
