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
 * @Description 存储与已触发的 Trigger 相关的状态信息，
 * 以及相联 Job 的执行信息。
 * @author wutao
 * @date 2020/9/28
 */
@ApiModel(description="存储已触发Trigger状态信息及Job 的执行信息")
@Data
@TableName(value = "QRTZ_FIRED_TRIGGERS")
@EqualsAndHashCode(callSuper = true)
public class QrtzFiredTriggers extends Model<QrtzFiredTriggers> {
    /**
     * 调度名称
     */
    @TableId(value = "SCHED_NAME")
    @ApiModelProperty(value="调度名称")
    private String schedName;

    /**
     * 调度器实例id
     */
    @ApiModelProperty(value="调度器实例id")
    private String entryId;

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
     * 调度器实例名
     */
    @ApiModelProperty(value="调度器实例名")
    private String instanceName;

    /**
     * 触发的时间
     */
    @ApiModelProperty(value="触发的时间")
    private Long firedTime;

    /**
     * 定时器制定的时间
     */
    @ApiModelProperty(value="定时器制定的时间")
    private Long schedTime;

    /**
     * 优先级
     */
    @ApiModelProperty(value="优先级")
    private Integer priority;

    /**
     * 状态
     */
    @ApiModelProperty(value="状态")
    private String state;

    /**
     * 集群中job的名字
     */
    @ApiModelProperty(value="集群中job的名字")
    private String jobName;

    /**
     * 集群中job的所属组的名字
     */
    @ApiModelProperty(value="集群中job的所属组的名字")
    private String jobGroup;

    /**
     * 是否并发
     */
    @ApiModelProperty(value="是否并发")
    private String isNonconcurrent;

    /**
     * 是否接受恢复执行，默认为false，
     * 设置了RequestsRecovery为true，则会被重新执行
     */
    @ApiModelProperty(value="是否接受恢复执行，默认为false，" +
            "设置了RequestsRecovery为true，则会被重新执行")
    private String requestsRecovery;
}