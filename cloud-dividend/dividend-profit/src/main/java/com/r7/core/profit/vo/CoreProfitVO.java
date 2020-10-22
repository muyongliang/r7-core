package com.r7.core.profit.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wutao
 * @Description 分润明细视图
 */
@ApiModel(description = "分润明细视图")
@Data
public class CoreProfitVO {
    /**
     * id
     */
    @TableId(value = "id")
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 平台id
     */
    @ApiModelProperty(value="平台id")
    private Long appId;

    /**
     * 分润方案id
     */
    @ApiModelProperty(value="分润方案id")
    private Long planId;

    /**
     * 分润用户id
     */
    @ApiModelProperty(value="分润用户id")
    private Long userId;

    /**
     * 发放记录id
     */
    @ApiModelProperty(value="发放记录id")
    private Long recordIncomeId;

    /**
     * 订单ID
     */
    @ApiModelProperty(value="订单ID")
    private Long orderId;

    /**
     * 层级
     */
    @ApiModelProperty(value="层级")
    private Integer level;

    /**
     * 分润金额
     */
    @ApiModelProperty(value="分润金额")
    private Integer amount;

    /**
     * 分润积分
     */
    @ApiModelProperty(value="分润积分")
    private Integer integral;

    /**
     * 计算状态 1未计算2已计算
     */
    @ApiModelProperty(value="计算状态 1未计算2已计算")
    private Integer status;

    /**
     * 分润比例
     */
    @ApiModelProperty(value="分润比例")
    private Integer rate;

    /**
     * 分润类型 1权益商品2游戏分润
     */
    @ApiModelProperty(value="分润类型 1权益商品2游戏分润")
    private Integer type;

    /**
     * 日期 yyyyMMdd
     */
    @ApiModelProperty(value="日期 yyyyMMdd")
    private Integer profitDate;

    /**
     * 小时
     */
    @ApiModelProperty(value="小时")
    private Integer hour;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String description;


    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createdAt;


}
