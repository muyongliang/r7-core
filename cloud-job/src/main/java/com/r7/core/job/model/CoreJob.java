package com.r7.core.job.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
//@TableName("core_job")
@ApiModel(description = "任务类")
public class CoreJob {
    /** id;任务id */
//    @Id
//    @GeneratedValue
    @ApiModelProperty(value = "id")
    private Long id ;

    @ApiModelProperty(value = "平台Id", example = "股票1/商城2/游戏3")
    private Long appId ;

    @ApiModelProperty(value = "任务名称")
    private String jobName ;

    @ApiModelProperty(value = "任务标识", example = "用于展示")
    private String jobCode ;

    @ApiModelProperty(value = "任务内容")
    private String content ;

    @ApiModelProperty(value = "任务规则")
    private String jobRule ;

    @ApiModelProperty(value = "任务完成人数")
    private Integer winnerNum ;

    @ApiModelProperty(value = "任务状态", example = "上架1/未上架2")
    private Integer status ;

    @ApiModelProperty(value = "任务上架时间", hidden = true)
    private Date onShelf ;

    @ApiModelProperty(value = "任务下架时间", hidden = true)
    private Date offShelf ;

    @ApiModelProperty(value = "创建人", hidden = true)
    private Long createdBy ;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createdAt ;

    @ApiModelProperty(value = "更新人", hidden = true)
    private Long updatedBy ;

    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updatedAt ;

}