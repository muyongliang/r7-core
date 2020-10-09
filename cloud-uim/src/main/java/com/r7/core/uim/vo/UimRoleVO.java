package com.r7.core.uim.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色视图
 *
 * @author zhongpingli
 * @date 2020-09-25
 */
@Data
@ApiModel(description = "角色视图")
public class UimRoleVO {

    @ApiModelProperty("id")
    private Long id;
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
    /**
     * 特殊角色;特定角色不展示给后端
     */
    @ApiModelProperty("特殊角色")
    private Integer feature;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;



}
