package com.r7.core.uim.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zs
 * @description: 用户角色关系展示
 * @date : 2020-10-12
 */
@Data
public class UimUserRoleVO {
    @ApiModelProperty("id")
    private Long id;
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
    private String code;
    /**
     * 角色名
     */
    @ApiModelProperty("角色名")
    private String roleName;
}
