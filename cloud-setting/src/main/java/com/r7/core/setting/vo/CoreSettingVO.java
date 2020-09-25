package com.r7.core.setting.vo;

import lombok.Data;

@Data
public class CoreSettingVO {

    /**
     * id
     */
    private Long id;
    /**
     * 平台id
     */
    private Long appId;
    /**
     * key;用于区分配置的不同模块前缀，如商城core_xxxx
     */
    private String key;
    /**
     * 配置名称;模块的名称
     */
    private String settingName;
    /**
     * 规则;配置的规则,value中存放json
     */
    private String rule;
    /**
     * 状态;-1禁用，1开启
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 详情描述;配置的详情描述
     */
    private String description;
    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 更新人
     */
    private Long updatedBy;


}
