package com.r7.core.trigger.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wutao
 * @Description 定时任务视图
 *
 * @date 2020/9/28
 */
@Data
@ApiModel(description = "定时任务视图")
public class CoreQuartzJobVO {
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
     * 任务路径
     */
    @ApiModelProperty(value="任务路径")
    private String jobClass;

    /**
     * 任务名称
     */
    @ApiModelProperty(value="任务名称")
    private String jobName;

    /**
     * 规则表达式
     */
    @ApiModelProperty(value="规则表达式")
    private String ruleExpression;

    /**
     * 错失策略 1下周期再执行/2错失周期执行一次/3错失周期立即执行
     */
    @ApiModelProperty(value="错失策略 1下周期再执行/2错失周期执行一次/3错失周期立即执行")
    private Integer misfire;

    /**
     * 任务状态（启动/暂停） -1异常1待启动2正常3暂停
     */
    @ApiModelProperty(value="任务状态（启动/暂停） -1异常1待启动2正常3暂停")
    private Integer status;

    /**
     * 任务描述
     */
    @ApiModelProperty(value="描述")
    private String description;

    /**
     * 启动时间
     */
    @ApiModelProperty(value="启动时间")
    private LocalDateTime startAt;

    /**
     * 恢复时间
     */
    @ApiModelProperty(value="恢复时间")
    private LocalDateTime regainAt;

    /**
     * 分组
     */
    @ApiModelProperty(value="分组")
    private String jobGroup;

     /**
     * 执行次数
     */
    @ApiModelProperty(value="执行次数")
    private Integer count;

    /**
     * 出错次数
     */
    @ApiModelProperty(value="出错次数")
    private Integer errorsNum;
}
