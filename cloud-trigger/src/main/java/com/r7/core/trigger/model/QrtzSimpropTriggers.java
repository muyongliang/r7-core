package com.r7.core.trigger.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @Description 存储CalendarIntervalTrigger
 * 和DailyTimeIntervalTrigger
 * @author wutao
 * @date 2020/9/28
 */
@ApiModel(description="存储CalendarIntervalTrigger和DailyTimeIntervalTrigger")
@Data
@TableName(value = "QRTZ_SIMPROP_TRIGGERS")
@EqualsAndHashCode(callSuper = true)
public class QrtzSimpropTriggers extends Model<QrtzSimpropTriggers> {
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
     * String类型的trigger的第一个参数
     */
    @ApiModelProperty(value="String类型的trigger的第一个参数")
    private String strProp1;

    /**
     * String类型的trigger的第二个参数
     */
    @TableField(value = "STR_PROP_2")
    @ApiModelProperty(value="String类型的trigger的第二个参数")
    private String strProp2;

    /**
     * String类型的trigger的第三个参数
     */
    @ApiModelProperty(value="String类型的trigger的第三个参数")
    private String strProp3;

    /**
     * int类型的trigger的第一个参数
     */
    @ApiModelProperty(value="int类型的trigger的第一个参数")
    private Integer intProp1;

    /**
     * int类型的trigger的第二个参数
     */
    @ApiModelProperty(value="int类型的trigger的第二个参数")
    private Integer intProp2;

    /**
     * long类型的trigger的第一个参数
     */
    @ApiModelProperty(value="long类型的trigger的第一个参数")
    private Long longProp1;

    /**
     * long类型的trigger的第二个参数
     */
    @ApiModelProperty(value="long类型的trigger的第二个参数")
    private Long longProp2;

    /**
     * decimal类型的trigger的第一个参数
     */
    @ApiModelProperty(value="decimal类型的trigger的第一个参数")
    private BigDecimal decProp1;

    /**
     * decimal类型的trigger的第二个参数
     */
    @ApiModelProperty(value="decimal类型的trigger的第二个参数")
    private BigDecimal decProp2;

    /**
     * Boolean类型的trigger的第一个参数
     */
    @ApiModelProperty(value="Boolean类型的trigger的第一个参数")
    private String boolProp1;

    /**
     * Boolean类型的trigger的第二个参数
     */
    @ApiModelProperty(value="Boolean类型的trigger的第二个参数")
    private String boolProp2;
}