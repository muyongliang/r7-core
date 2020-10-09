package com.r7.core.uim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户角色传输层
 *
 * @author zhongpingli
 * @date 2020-09-25
 */
@Data
@ApiModel(description = "用户角色传输层")
public class UimRoleDTO {

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    @NotBlank(message = "角色编码不能为空")
    private String code;
    /**
     * 角色名
     */
    @ApiModelProperty("角色名")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;
    /**
     * 特殊角色;特定角色不展示给后端
     */
    @ApiModelProperty("特殊角色")
    @NotNull(message = "特殊角色不能为空。")
    private Integer feature;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    @NotNull(message = "排序不能为空。")
    private Integer sort;

}
