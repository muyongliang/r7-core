package com.r7.core.profit.dto;

import com.r7.core.profit.constant.CalculationStatusEnum;
import com.r7.core.profit.constant.ProfitTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author wutao
 * @Description 分润明细传输层
 */
@ApiModel(description = "分润明细传输层")
@Data
public class CoreProfitDTO  {

    /**
     * 平台id
     */
    @NotNull(message = "平台id不能为空")
    @ApiModelProperty(value="平台id")
    private Long appId;

    /**
     * 分润方案id
     */
    @NotNull(message = "分润方案id不能为空")
    @ApiModelProperty(value="分润方案id")
    private Long planId;

    /**
     * 分润用户id
     */
    @NotNull(message = "分润用户id不能为空")
    @ApiModelProperty(value="分润用户id")
    private Long userId;

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空")
    @ApiModelProperty(value="订单ID")
    private Long orderId;

    /**
     * 层级
     */
    @NotNull(message = "层级不能为空")
    @ApiModelProperty(value="层级")
    private Integer level;

    /**
     * 分润金额
     */
    @NotNull(message = "分润金额不能为空")
    @ApiModelProperty(value="分润金额")
    private Integer amount;

    /**
     * 分润积分
     */
    @NotNull(message = "分润积分不能为空")
    @ApiModelProperty(value="分润积分")
    private Integer integral;

    /**
     * 计算状态 1未计算2已计算
     */
    @NotNull(message = "计算状态不能为空")
    @ApiModelProperty(value="计算状态 1未计算2已计算")
    private CalculationStatusEnum status;

    /**
     * 分润比例
     */
    @NotNull(message = "分润比例不能为空")
    @ApiModelProperty(value="分润比例")
    private Integer rate;

    /**
     * 分润类型 1权益商品2游戏分润
     */
    @NotNull(message = "分润类型不能为空")
    @ApiModelProperty(value="分润类型 1权益商品2游戏分润")
    private ProfitTypeEnum type;

    /**
     * 分润标识
     */
    @NotNull(message = "分润标识不能为空")
    @ApiModelProperty(value="分润标识 1金额2积分")
    private Integer difference;

    /**
     * 描述
     */
    @NotBlank(message = "描述不能为空")
    @ApiModelProperty(value="描述")
    private String description;
}
