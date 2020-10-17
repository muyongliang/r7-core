package com.r7.core.uim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author zs
 * @description:
 * @date : 2020-10-14
 */
@Data
@ApiModel("用户认证订单传输实体")
public class UimOauthOrderDTO {

    /**
     * 用户ID
     */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;
    /**
     * 认证类型;实名认证，视频认证，电子签认证
     */
    @ApiModelProperty("认证类型")
    @NotNull(message = "认证类型不能为空")
    private Integer type;
    /**
     * 认证项目key;比如实名认证中，身份证人头面对应的key，或者国徽面对应的key，审核内容对应的key
     */
    @ApiModelProperty("认证项目key")
    @NotEmpty(message = "认证项目key不能为空")
    private String oauthKey;
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
     * 排序
     */
    @ApiModelProperty("排序")
    @NotNull(message = "排序字段不能为空")
    private Integer sort;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @NotEmpty(message = "描述不能为空")
    private String description;
}
