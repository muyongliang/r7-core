package com.r7.core.trigger.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wutao
 * @Description
 * @date 2020/9/30
 */
@Data
@ApiModel(description = "用来修改定时任务信息显示层")
public class CoreQuartzJobUpdateVO {

    /**
     * id
     */
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 规则表达式
     */
    @ApiModelProperty(value="规则表达式")
    private String ruleExpression;

    /**
     * 任务执行情况描述
     */
    @ApiModelProperty(value="任务执行情况描述")
    private String description;
}
