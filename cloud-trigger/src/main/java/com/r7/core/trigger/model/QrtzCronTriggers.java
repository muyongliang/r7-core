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
 * @Description 存储触发器的cron表达式表
 * @author wutao
 * @date 2020/9/28
 */
@ApiModel(value="存储触发器的cron表达式表")
@Data
@TableName(value = "QRTZ_CRON_TRIGGERS")
@EqualsAndHashCode(callSuper = true)
public class QrtzCronTriggers extends Model<QrtzCronTriggers> {
    /**
     * 调度名称
     */
    @TableId(value = "SCHED_NAME")
    @ApiModelProperty(value="调度名称")
    private String schedName;

    /**
     * qrtz_triggers表trigger_name的外键
     */
    @ApiModelProperty(value="qrtz_triggers表trigger_name的外键")
    private String triggerName;

    /**
     * qrtz_triggers表trigger_group的外键
     */
    @ApiModelProperty(value="qrtz_triggers表trigger_group的外键")
    private String triggerGroup;

    /**
     * cron表达式
     */
    @ApiModelProperty(value="cron表达式")
    private String cronExpression;

    /**
     * 时区
     */
    @ApiModelProperty(value="时区")
    private String timeZoneId;
}