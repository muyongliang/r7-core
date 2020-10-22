package com.r7.core.gateway.vo;

import com.r7.core.gateway.constant.AuthConstant;
import com.r7.core.gateway.constant.PermissionEnum;
import lombok.Data;

/**
 * 角色资源
 *
 * @author zhongpingli
 */
@Data
public class UimRoleResourceVO {

    /**
     * 平台ID
     */
    private Long appId;

    /**
     * 资源ID
     */
    private String resourceUrl;

    /**
     * 资源类型
     */
    private PermissionEnum permission;

    /**
     * 角色编码
     */
    private String roleCode;


    public void setRoleCode(String roleCode) {
        this.roleCode = AuthConstant.AUTHORITY_PREFIX + roleCode;
    }


    public UimResourceInfoVo toUimResourceInfoVo() {
        UimResourceInfoVo uimResourceInfoVo = new UimResourceInfoVo();
        uimResourceInfoVo.setPermission(permission);
        uimResourceInfoVo.setUrl(resourceUrl);
        return uimResourceInfoVo;
    }
}
