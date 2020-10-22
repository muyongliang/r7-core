package com.r7.core.uim.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zs
 * @description: 用户认证订单展示
 * @date : 2020-10-14
 */
@Data
@ApiModel("用户认证订单展示")
public class UimOauthOrderVO {

    @ApiModelProperty("id")
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 平台ID
     */
    @ApiModelProperty("平台ID")
    private Long appId;
    /**
     * 认证类型;实名认证，视频认证，电子签认证
     */
    @ApiModelProperty("认证类型")
    private Integer type;
    /**
     * 认证项目key;比如实名认证中，身份证人头面对应的key，或者国徽面对应的key，审核内容对应的key
     */
    @ApiModelProperty("认证项目key")
    private String oauthKey;
    /**
     * 审核内容;统一使用json格式存储
     */
    @ApiModelProperty("审核内容")
    private String context;
    /**
     * 审核状态 1未审核，2待审核，3审核通过，4审核驳回
     */
    @ApiModelProperty("审核状态")
    private Integer status;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
}
