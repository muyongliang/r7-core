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
 * @Description 以 Blob 类型存储存放日历信息，
 * quartz可配置一个日历来指定一个时间范围。
 * @author wutao
 * @date 2020/9/28
 */
@ApiModel(description="存储存放日历信息")
@Data
@TableName(value = "QRTZ_CALENDARS")
@EqualsAndHashCode(callSuper = true)
public class QrtzCalendars extends Model<QrtzCalendars> {
    /**
     * 调度名称
     */
    @TableId(value = "SCHED_NAME")
    @ApiModelProperty(value="调度名称")
    private String schedName;

    /**
     * 日历名称
     */
    @ApiModelProperty(value="日历名称")
    private String calendarName;

    /**
     * 一个blob字段，存放持久化calendar对象
     */
    @ApiModelProperty(value="一个blob字段，存放持久化calendar对象")
    private byte[] calendar;
}