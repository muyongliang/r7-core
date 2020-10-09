package com.r7.core.trigger.dto;


import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wutao
 * @Descriptionb 定时任务传输层
 * @date 2020/9/28
 */
@Data
@ApiModel(description = "定时任务传输层")
public class CoreQuartzJobDTO {
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value="id")
    private Long id;
    /**
     * 平台id
     */
    @NotNull(message = "平台id不能为空")
    @ApiModelProperty(value="平台id")
    private Long appId;

    /**
     * 任务路径
     */
    @NotBlank(message = "任务路径不能为空")
    @ApiModelProperty(value="任务路径")
    private String jobClass;

    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空")
    @ApiModelProperty(value="任务名称")
    private String jobName;

    /**
     * 规则表达式
     */
    @NotBlank(message = "规则表达式不能为空")
    @ApiModelProperty(value="规则表达式")
    private String ruleExpression;

    /**
     * 错失策略 1下周期再执行/2错失周期执行一次/3错失周期立即执行
     */
    @NotNull(message = "错失执行策略不能为空")
    @ApiModelProperty(value="错失策略 1下周期再执行/2错失周期执行一次/3错失周期立即执行")
    private Integer misfire;

    /**
     * 任务状态（启动/暂停） -1异常1待启动2正常3暂停
     */
    @NotNull(message = "任务状态不能为空")
    @ApiModelProperty(value="任务状态（启动/暂停） -1异常1待启动2正常3暂停，默认1")
    private Integer status;

    /**
     * 分组
     */
    @NotBlank(message = "定时任务分组不能为空")
    @ApiModelProperty(value="分组")
    private String jobGroup;

    /**
     * 任务描述
     */
    @ApiModelProperty(value="描述")
    private String description;
}
