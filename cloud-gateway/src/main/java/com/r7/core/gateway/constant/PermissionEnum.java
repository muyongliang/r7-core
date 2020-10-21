package com.r7.core.gateway.constant;



/**
 * 权限
 *
 * @author zhongpingli
 */
public enum PermissionEnum  {
    /**
     * 游览
     * 新增
     * 修改
     * 删除
     */
    BROWSE(1),
    CREATE(2),
    UPDATE(3),
    DELETE(4),
    OTHER(5);


    private Integer code;

    PermissionEnum(Integer code) {
        this.code = code;
    }

}
