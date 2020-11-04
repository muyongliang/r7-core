package com.r7.core.assets.funds.dto;

import com.r7.core.assets.funds.constant.FundsChannelEnum;
import com.r7.core.assets.funds.constant.FundsStatusEnum;
import com.r7.core.assets.funds.constant.FundsTransactionStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zs
 * @description: 资金传输层
 * @date : 2020-10-28
 */
@Data
@ApiModel("资金流动传输层")
public class CoreFundsDTO {
    /**
     * 内部订单号
     */
    @ApiModelProperty("内部订单号")
    @NotNull(message = "内部订单号不能为空")
    private Long inOrderSn;
    /**
     * 外部订单号
     */
    @ApiModelProperty("外部订单号")
    @NotNull(message = "外部订单号不能为空")
    private Long outOrderSn;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;
    /**
     * 支付链接
     */
    @ApiModelProperty("支付链接")
    @NotBlank(message = "支付链接不能为空")
    private String payLink;
    /**
     * 交易金额
     */
    @ApiModelProperty("交易金额")
    @NotNull(message = "交易金额不能为空")
    private Integer amount;
    /**
     * 支付时间
     */
    @ApiModelProperty("支付时间")
    private Date payDate;
    /**
     * 支付状态;-1交易失败，1交易成功，2待支付，3取消支付
     */
    @ApiModelProperty(value = "支付状态", example = "，1交易成功，2交易失败，3待支付，4取消支付")
    @NotNull(message = "支付状态不能为空")
    private FundsStatusEnum status;
    /**
     * 交易状态;1待支付2已支付3支付失败
     */
    @ApiModelProperty(value = "交易状态", example = "1待支付2已支付3支付失败")
    @NotNull(message = "交易状态不能为空")
    private FundsTransactionStatusEnum transactionStatus;
    /**
     * 支付渠道;1支付宝2微信3其他
     */
    @ApiModelProperty(value = "支付渠道", example = "1支付宝2微信3其他")
    @NotNull(message = "支付渠道不能为空")
    private FundsChannelEnum channel;
    /**
     * 交易订单详情描述
     */
    @ApiModelProperty("交易订单详情描述")
    @NotBlank(message = "交易订单详情描述不能为空")
    private String description;
}
