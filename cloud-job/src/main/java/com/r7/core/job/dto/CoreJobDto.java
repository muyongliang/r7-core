package com.r7.core.job.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 任务信息传输层
 * @author zs
 */
@Data
@ApiModel(description = "任务类")
public class CoreJobDto {
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
    @Length(min = 1, max = 32, message = "任务标识不能超过32位")
    @NotBlank(message = "任务标识不能为空")
    private String jobCode ;
    /**
     * 任务内容
     */
    @ApiModelProperty(value = "任务内容")
    @NotBlank(message = "任务内容不能为空")
    private String content ;
    /**
     * 完成人数
     */
    @ApiModelProperty(value = "任务完成人数")
    @NotNull(message = "完成人数默认为0")
    private Integer winnerNum;
    /**
     * 任务规则
     */
    @ApiModelProperty(value = "任务规则")
    @NotBlank(message = "任务规则不能为空")
    private String jobRule ;
    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态", example = "上架1/未上架2")
    @Range(min = 1, max = 2, message = "上架1/未上架2")
    private Integer status ;
    /**
     * 任务上架时间
     */
    @ApiModelProperty(value = "任务上架时间")
    @NotNull(message = "任务上架时间不能为空")
    private Date onShelf ;

}