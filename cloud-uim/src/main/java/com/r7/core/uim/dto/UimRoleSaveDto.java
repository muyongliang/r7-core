package com.r7.core.uim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增角色
 *
 * @author zhongpingli
 */
@Data
@ApiModel("新增角色实体")
public class UimRoleSaveDto {

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    @NotBlank(message = "角色编码不能为空")
    private String code;
    /**
     * 角色名
     */
    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;
}
