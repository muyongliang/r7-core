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
 * @Description 存储程序的锁的信息
 * @author wutao
 * @date 2020/9/28
 */
@ApiModel(description="存储程序的锁的信息")
@Data
@TableName(value = "QRTZ_LOCKS")
@EqualsAndHashCode(callSuper = true)
public class QrtzLocks extends Model<QrtzLocks> {
    /**
     * 调度名称
     */
    @TableId(value = "SCHED_NAME")
    @ApiModelProperty(value="调度名称")
    private String schedName;

    /**
     * 悲观锁名称
     */
    @ApiModelProperty(value="悲观锁名称")
    private String lockName;
}