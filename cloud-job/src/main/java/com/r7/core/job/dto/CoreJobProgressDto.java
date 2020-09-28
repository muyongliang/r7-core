package com.r7.core.job.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 任务进度信息传输层
 * @author zs
 */
@Data
@ApiModel(description = "任务进度明细")
public class CoreJobProgressDto {
    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private Long userId ;

    /** 任务标识 */
    @ApiModelProperty(value = "任务标识")
    private String jobCode ;

    /** 任务执行状态;-1执行失败；1待执行；2执行中；3执行成功 */
    @ApiModelProperty(value = "任务执行状态", example = "-1执行失败；1待执行；2执行中；3执行成功")
    private Integer status ;

    /** 任务的进度json格式;如用户购买商品付款时调用接口进行记录判断（存在问题）json 存储用户任务完成记录，
     * 签到每天触发一次。认证是一个用户只能触发，根据规则判断任务的情况 */
    @ApiModelProperty(value = "任务进度")
    private String progress ;

    /** 是否发放;1否；2是 */
    @ApiModelProperty(value = "是否发放", example = "1否；2是")
    private Integer isDistribution ;
}