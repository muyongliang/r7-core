package com.r7.core.uim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: zs
 * @description: 用户认证订单修改传输层
 * @date: 2020-11-05
 **/
@Data
@ApiModel("用户认证订单修改传输层")
public class UimOauthOrderUpdateDTO {
    /**
     * 审核内容;统一使用json格式存储
     */
    @ApiModelProperty("审核内容")
    @NotEmpty(message = "审核内容不能为空")
    private String context;
    /**
     * 审核状态 1未审核，2待审核，3审核通过，4审核驳回
     */
    @ApiModelProperty("审核状态")
    @NotNull(message = "审核状态不能为空")
    private Integer status;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @NotEmpty(message = "描述不能为空")
    private String description;
}
