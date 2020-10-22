package com.r7.core.trigger.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @Description 存储简单的 Trigger，包括重复次数，
 * 间隔，以及已触发的次数
 * @author wutao
 * @date 2020/9/28
 */
@ApiModel(description="存储简单的Trigger包括重复次数等")
@Data
@TableName(value = "QRTZ_SIMPLE_TRIGGERS")
@EqualsAndHashCode(callSuper = true)
public class QrtzSimpleTriggers extends Model<QrtzSimpleTriggers> {
    /**
     * 调度名称
     */
    @TableId(value = "SCHED_NAME")
    @ApiModelProperty(value="调度名称")
    private String schedName;

    /**
     * qrtz_triggers表trigger_ name的外键
     */
    @ApiModelProperty(value="qrtz_triggers表trigger_ name的外键")
    private String triggerName;

    /**
     * qrtz_triggers表trigger_group的外键
     */
    @ApiModelProperty(value="qrtz_triggers表trigger_group的外键")
    private String triggerGroup;

    /**
     * 重复的次数统计
     */
    @ApiModelProperty(value="重复的次数统计")
    private Long repeatCount;

    /**
     * 重复的间隔时间
     */
    @ApiModelProperty(value="重复的间隔时间")
    private Long repeatInterval;

    /**
     * 已经触发的次数
     */
    @ApiModelProperty(value="已经触发的次数")
    private Long timesTriggered;
}