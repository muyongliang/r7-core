package com.r7.core.uim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author zs
 * @description: 冻结传输对象
 * @date : 2020-10-13
 */
@Data
@ApiModel("修改冻结传输对象")
public class UimChillUpdateDTO {
    /**
     * 冻结资源;登录/邀请码/提现/平台冻结
     */
    @ApiModelProperty("冻结资源id")
    @NotNull(message = "资源id不能为空")
    private Long resourceId;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @NotEmpty(message = "描述不能为空")
    private String description;
}
