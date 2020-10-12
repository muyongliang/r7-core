package com.r7.core.uim.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zs
 * @description:
 * @date : 2020-10-12
 */
@Data
public class UimUserRoleBindVO {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private Long roleId;
    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String roleCode;
    /**
     * 角色名
     */
    @ApiModelProperty("角色名")
    private String roleName;
}
