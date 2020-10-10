package com.r7.core.trigger.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wutao
 * @Description 用来修改定时任务信息的传输层
 * @date 2020/9/29
 */
@Data
@ApiModel(description = "用来修改定时任务信息的传输层")
public class CoreQuartzJobUpdateDTO {

    /**
     * id
     */
    @NotNull(message ="定时任务id不能为空")
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 规则表达式
     */
    @NotBlank(message = "规则表达式不能为空")
    @ApiModelProperty(value="规则表达式")
    private String ruleExpression;

    /**
     * 任务执行情况描述
     */
    @ApiModelProperty(value="任务执行情况描述")
    private String description;
}
