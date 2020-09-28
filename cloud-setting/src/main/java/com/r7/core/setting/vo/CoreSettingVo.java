package com.r7.core.setting.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(description = "公共配置信息")
@Data
public class CoreSettingVo {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;
    /**
     * 平台id
     */
    @ApiModelProperty("平台id")
    private Long appId;
    /**
     * key;用于区分配置的不同模块前缀，如商城core_xxxx
     */
    @ApiModelProperty("用于区分配置的不同模块前缀，如商城core_xxxx")
    private String key;
    /**
     * 配置名称;模块的名称
     */
    @ApiModelProperty("模块的名称")
    private String settingName;
    /**
     * 规则;配置的规则,value中存放json
     */
    @ApiModelProperty("配置的规则")
    private String rule;
    /**
     * 状态;-1禁用，1开启
     */
    @ApiModelProperty("状态;-1禁用，1开启")
    private Integer status;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
    /**
     * 详情描述;配置的详情描述
     */
    @ApiModelProperty("配置的详情描述")
    private String description;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Long createdBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createdAt;
    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private Long updatedBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updatedAt;


}
