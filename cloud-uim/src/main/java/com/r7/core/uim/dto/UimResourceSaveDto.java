package com.r7.core.uim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 新增资源传输层
 *
 * @author zhongpingli
 */
@Data
@ApiModel("新增资源传输层")
public class UimResourceSaveDto {

    /**
     * 资源父类
     */
    @ApiModelProperty("资源父类")
    @NotNull(message = "资源父ID不能为空")
    private Long pId;

    /**
     * 资源标识
     */
    @ApiModelProperty("资源标识")
    @NotBlank(message = "资源标识不能为空。")
    private String code;
    /**
     * 资源名称
     */
    @ApiModelProperty("资源名称")
    @NotBlank(message = "资源名称不能为空。")
    private String resourceName;
    /**
     * 资源地址
     */
    @ApiModelProperty("资源地址")
    @NotBlank(message = "资源地址不能为空。")
    private String url;
    /**
     * 资源类型;0菜单/1按钮
     */
    @ApiModelProperty("资源类型")
    @NotNull(message = "资源类型不能为空")
    private Integer type;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    @NotNull(message = "排序不能为空。")
    private Integer sort;

}
