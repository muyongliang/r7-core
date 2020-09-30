package com.r7.core.job.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 任务信息传输层
 * @author zs
 */
@Data
@ApiModel(description = "任务类")
public class CoreJobStatusDto {
    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    private String jobName ;
    /**
     * 任务标识
     */
    @ApiModelProperty(value = "任务标识", example = "用于展示")
    @Size(max = 32)
    @NotBlank(message = "任务标识不能为空")
    private String jobCode ;
    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态", example = "上架1/未上架2")
    @NotNull(message = "任务状态不能为空，默认为2")
    private Integer status ;
    /**
     * 任务下架时间
     */
    @ApiModelProperty(value = "任务下架时间")
    @NotNull(message = "任务下架时间不能为空")
    private Date offShelf ;

}