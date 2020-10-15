package com.r7.core.uim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zs
 * @description: 用户认证传输实体
 * @date : 2020-10-14
 */
@Data
@ApiModel("用户认证信息传输实体")
public class UimOauthDTO {
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    @NotNull(message = "用户id不能为空")
    private Long userId;
    /**
     * 认证订单id
     */
    @ApiModelProperty("认证订单id")
    @NotNull(message = "认证订单id不能为空")
    private Long oauthOrderId;
    /**
     * 认证类型 1实名认证，2视频认证，3电子签认证
     */
    @ApiModelProperty("认证类型")
    @NotNull(message = "认证类型不能为空")
    private Integer type;
    /**
     * 认证状态
     */
    @ApiModelProperty("认证状态")
    @NotNull(message = "认证状态不能为空")
    private Integer status;
    /**
     * 驳回原因
     */
    @ApiModelProperty("驳回原因")
    private String reason;
}
