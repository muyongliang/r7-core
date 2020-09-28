package com.r7.core.job.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zs
 * @description: 任务状态传输层
 * @date : 2020-09-28
 */

@Data
@ApiModel(value = "任务状态实体")
public class CoreJobStatusDto {
    @ApiModelProperty(value = "id")
    private Long id ;

    @ApiModelProperty(value = "任务名称")
    private String jobName ;

    @ApiModelProperty(value = "任务标识", example = "用于展示")
    private String jobCode ;

    @ApiModelProperty(value = "任务状态", example = "上架1/未上架2")
    private Integer status ;

    @ApiModelProperty(value = "任务上架时间", hidden = true)
    private Date onShelf ;
}
