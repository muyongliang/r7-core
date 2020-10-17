package com.r7.core.uim.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zs
 * @description: 用户认证展示
 * @date : 2020-10-14
 */
@Data
@ApiModel("用户认证展示")
public class UimOauthVO {
    @ApiModelProperty("id")
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;
    /**
     * 平台ID
     */
    @ApiModelProperty("平台ID")
    private Long appId;
    /**
     * 认证订单id
     */
    @ApiModelProperty("认证订单id")
    private Long oauthOrderId;
    /**
     * 认证类型 实名认证，视频认证，电子签认证
     */
    @ApiModelProperty("认证类型")
    private Integer type;
    /**
     * 认证状态
     */
    @ApiModelProperty("认证状态")
    private Integer status;
    /**
     * 驳回原因
     */
    @ApiModelProperty("驳回原因")
    private String reason;
}
