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
 * @Description 调度实例的基本信息，
 * quartz会定时读取该表的信息判断集群中每个实例的当前状态
 * @author wutao
 * @date 2020/9/28
 */
@ApiModel(description="调度实例的基本信息")
@Data
@TableName(value = "QRTZ_SCHEDULER_STATE")
@EqualsAndHashCode(callSuper = true)
public class QrtzSchedulerState extends Model<QrtzSchedulerState> {
    /**
     * 调度名称
     */
    @TableId(value = "SCHED_NAME")
    @ApiModelProperty(value="调度名称")
    private String schedName;

    /**
     * 之前配置文件中org.quartz.scheduler.instanceId配置的名字，
     * 就会写入该字段
     */
    @ApiModelProperty(value="之前配置文件中org.quartz.scheduler.instanceId配置的名字，就会写入该字段")
    private String instanceName;

    /**
     * 上次检查时间
     */
    @ApiModelProperty(value="上次检查时间")
    private Long lastCheckinTime;

    /**
     * 检查间隔时间
     */
    @ApiModelProperty(value="检查间隔时间")
    private Long checkinInterval;
}