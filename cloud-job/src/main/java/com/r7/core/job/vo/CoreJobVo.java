package com.r7.core.job.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description: 任务信息
 * @author: zs
 **/
@Data
@ApiModel(description = "任务类")
public class CoreJobVo {
    /**
     * id;任务id
     */
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 平台id
     */
    @ApiModelProperty(value = "平台Id", example = "股票1/商城2/游戏3")
    private Long appId;
    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")
    private String jobName;
    /**
     * 任务标识
     */
    @ApiModelProperty(value = "任务标识", example = "用于展示")
    private String jobCode;
    /**
     * 任务内容
     */
    @ApiModelProperty(value = "任务内容")
    private String content;
    /**
     * 任务规则
     */
    @ApiModelProperty(value = "任务规则")
    private String jobRule;
    /**
     * 任务完成人数
     */
    @ApiModelProperty(value = "任务完成人数")
    private Integer winnerNum;
    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态", example = "上架1/未上架2")
    private Integer status;
    /**
     * 任务上架时间
     */
    @ApiModelProperty(value = "任务上架时间")
    private Date onShelf;
    /**
     * 任务下架时间
     */
    @ApiModelProperty(value = "任务下架时间")
    private Date offShelf;
}