package com.r7.core.trigger.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @Description 保存触发器的基本信息
 * @author wutao
 * @date 2020/9/28
 */
@ApiModel(description="保存触发器的基本信息")
@Data
@TableName(value = "QRTZ_TRIGGERS")
@EqualsAndHashCode(callSuper = true)
public class QrtzTriggers extends Model<QrtzTriggers> {
    /**
     * 调度名称
     */
    @TableId(value = "SCHED_NAME")
    @ApiModelProperty(value="调度名称")
    private String schedName;

    /**
     * 触发器的名字
     */
    @ApiModelProperty(value="触发器的名字")
    private String triggerName;

    /**
     * 触发器所属组的名字
     */
    @ApiModelProperty(value="触发器所属组的名字")
    private String triggerGroup;

    /**
     * qrtz_job_details表job_name的外键
     */
    @ApiModelProperty(value="qrtz_job_details表job_name的外键")
    private String jobName;

    /**
     * qrtz_job_details表job_group的外键
     */
    @ApiModelProperty(value="qrtz_job_details表job_group的外键")
    private String jobGroup;

    /**
     * 详细描述信息
     */
    @ApiModelProperty(value="详细描述信息")
    private String description;

    /**
     * 下一次触发时间，默认为-1，意味不会自动触发
     */
    @ApiModelProperty(value="下一次触发时间，默认为-1，意味不会自动触发")
    private Long nextFireTime;

    /**
     * 上一次触发时间（毫秒）
     */
    @ApiModelProperty(value="上一次触发时间（毫秒）")
    private Long prevFireTime;

    /**
     * 优先级
     */
    @ApiModelProperty(value="优先级")
    private Integer priority;

    /**
     * 当前触发器状态
     */
    @TableField(value = "TRIGGER_STATE")
    @ApiModelProperty(value="当前触发器状态")
    private String triggerState;

    /**
     * 触发器的类型，使用cron表达式
     */
    @ApiModelProperty(value="触发器的类型，使用cron表达式")
    private String triggerType;

    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private Long startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private Long endTime;

    /**
     * 日程表名称，表qrtz_calendars的calendar_name字段外键
     */
    @ApiModelProperty(value="日程表名称，表qrtz_calendars的calendar_name字段外键")
    private String calendarName;

    /**
     * 措施或者是补偿执行的策略
     */
    @ApiModelProperty(value="措施或者是补偿执行的策略")
    private Integer misfireInstr;

    /**
     * 一个blob字段，存放持久化job对象
     */
    @ApiModelProperty(value="一个blob字段，存放持久化job对象")
    private byte[] jobData;
}