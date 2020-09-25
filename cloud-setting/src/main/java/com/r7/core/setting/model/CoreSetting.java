package com.r7.core.setting.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * @Author liang
 * @Date 2020/9/25 11:04
 * @Description 配置表
 */
@TableName("core_setting")
@Data
public class CoreSetting  extends Model<CoreSetting> {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 平台id
     */
    private Long appId;
    /**
     * key;用于区分配置的不同模块前缀，如商城core_xxxx
     */
    @TableField(value = "`key`")
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
    @TableField(value = "`status`")
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
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新人
     */
    private Long updatedBy;
    /**
     * 更新时间
     */
    private Date updatedAt;
}
