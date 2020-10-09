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
 * @Description 存储已暂停的 Trigger 组的信息
 * @author wutao
 * @date 2020/9/28
 */
@ApiModel(description="存储已暂停的 Trigger 组的信息")
@Data
@TableName(value = "QRTZ_PAUSED_TRIGGER_GRPS")
@EqualsAndHashCode(callSuper = true)
public class QrtzPausedTriggerGrps extends Model<QrtzPausedTriggerGrps> {
    /**
     * 调度名称
     */
    @TableId(value = "SCHED_NAME")
    @ApiModelProperty(value="调度名称")
    private String schedName;

    /**
     * qrtz_triggers表trigger_group的外键
     */
    @ApiModelProperty(value="qrtz_triggers表trigger_group的外键")
    private String triggerGroup;
}