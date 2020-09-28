package com.r7.core.setting.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author liang
 * @Date 2020/9/25 11:04
 * @Description 配置表查询
 */
@ApiModel(description = "公共配置信息")
@Data
public class CoreSettingDto {
    /**
     * 平台id
     */
    @NotEmpty(message = "appId不能为空")
    @ApiModelProperty("平台id")
    private Long appId;
    /**
     * key;用于区分配置的不同模块前缀，如商城core_xxxx
     */
    @ApiModelProperty("用于区分配置的不同模块前缀，如商城core_xxxx")
    @NotEmpty(message = "key不能为空")
    private String key;
    /**
     * 配置名称;模块的名称
     */
    @ApiModelProperty("模块的名称")
    @NotEmpty(message = "settingName不能为空")
    private String settingName;
    /**
     * 规则;配置的规则,value中存放json
     */
    @ApiModelProperty("配置的规则")
    @NotEmpty(message = "rule不能为空")
    private String rule;
    /**
     * 状态;-1禁用，1开启
     */
    @ApiModelProperty("状态;-1禁用，1开启")
    @NotEmpty(message = "status不能为空")
    private Integer status;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    @NotNull(message = "sort不能为空")
    private Integer sort;
    /**
     * 详情描述;配置的详情描述
     */
    @ApiModelProperty("配置的详情描述")
    @NotEmpty(message = "description不能为空")
    private String description;


}