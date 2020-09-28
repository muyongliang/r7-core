package com.r7.core.job.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 任务信息传输层
 * @author zs
 */
@Data
@ApiModel(description = "任务类")
public class CoreJobDto {

    @ApiModelProperty(value = "任务名称")
    private String jobName ;

    @ApiModelProperty(value = "任务标识", example = "用于展示")
    private String jobCode ;

    @ApiModelProperty(value = "任务内容")
    private String content ;

    @ApiModelProperty(value = "任务规则")
    private String jobRule ;

    @ApiModelProperty(value = "任务状态", example = "上架1/未上架2")
    private Integer status ;

    @ApiModelProperty(value = "任务上架时间")
    private Date onShelf ;

}