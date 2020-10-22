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
 * @Description  Trigger 作为 Blob 类型存储(用于 Quartz 用户用 JDBC
 * 创建他们自己定制的 Trigger 类型，
 * JobStore 并不知道如何存储实例的时候)
 * @author wutao
 * @date 2020/9/28
 */
@ApiModel(description="存储自定义的Trigger 类型")
@Data
@TableName(value = "QRTZ_BLOB_TRIGGERS")
@EqualsAndHashCode(callSuper = true)
public class QrtzBlobTriggers extends Model<QrtzBlobTriggers> {
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
     * 一个blob字段，存放持久化Trigger对象
     */
    @ApiModelProperty(value="一个blob字段，存放持久化Trigger对象")
    private byte[] blobData;
}