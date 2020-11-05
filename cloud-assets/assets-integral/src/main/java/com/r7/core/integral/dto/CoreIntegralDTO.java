package com.r7.core.integral.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wt
 * @Description 当前用户积分传输层
 */
@Data
@ApiModel(value = "当前用户积分传输层")
public class CoreIntegralDTO {


    /**
     * 当前用户id
     */
    @NotNull(message = "当前用户id不能为空")
    @ApiModelProperty(value = "当前用户id")
    private Long userId;

    /**
     * 当前用户的总积分
     */
    @NotNull(message = "当前用户的总积分不能为空")
    @ApiModelProperty(value = "当前用户的总积分")
    private Integer total;


}