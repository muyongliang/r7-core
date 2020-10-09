package com.r7.core.trigger.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wutao
 * @Description 定时任务执行情况
 * @date 2020/9/28
 */
@Data
@ApiModel(description = "定时任务执行情况视图")
public class CoreQuartzJobInfoVO {
    /**
     * id
     */
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 平台id
     */
    @ApiModelProperty(value="平台id")
    private Long appId;

    /**
     * 平台id
     */
    @ApiModelProperty(value="任务id")
    private Long jobId;

    /**
     * 任务执行情况描述
     */
    @ApiModelProperty(value="任务执行情况描述")
    private String description;

    /**
     * 状态 -1异常1未启动2正常3正在执行4暂停
     * @NotNull(message = "状态不能为空")
     *@ApiModelProperty(value="状态 -1异常1未启动2正常3正在执行4暂停")
     *  */
    private Integer status;


    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createdAt;

    /**
     * 作业执行开始时间
     */
    @ApiModelProperty(value="作业执行开始时间")
    private LocalDateTime startAt;
    /**
     * 作业执行结束时间
     */
    @ApiModelProperty(value="作业执行结束时间")
    private LocalDateTime endAt;

}
