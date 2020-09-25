package com.r7.core.uim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户角色传输层
 *
 * @author zhongpingli
 * @date 2020-09-25
 */
@Data
@ApiModel(description = "用户角色传输层")
public class UimRoleDto {

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
