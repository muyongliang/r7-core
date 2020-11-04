package com.r7.core.assets.funds.vo;

import com.r7.core.assets.funds.constant.FundsChannelEnum;
import com.r7.core.assets.funds.constant.FundsStatusEnum;
import com.r7.core.assets.funds.constant.FundsTransactionStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zs
 * @description: 资金展示实体
 * @date : 2020-10-28
 */
@Data
@ApiModel("资金展示实体")
public class CoreFundsVO {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;
    /**
     * 平台id
     */
    @ApiModelProperty("平台id")
    private Long appId;
    /**
     * 内部订单号
     */
    @ApiModelProperty("内部订单号")
    private Long inOrderSn;
    /**
     * 外部订单号
     */
    @ApiModelProperty("外部订单号")
    private Long outOrderSn;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organId;
    /**
     * 支付链接
     */
    @ApiModelProperty("支付链接")
    private String payLink;
    /**
     * 交易金额
     */
    @ApiModelProperty("交易金额")
    private Integer amount;
    /**
     * 支付时间
     */
    @ApiModelProperty("支付时间")
    private Date payDate;
    /**
     * 支付状态;-1交易失败，1交易成功，2待支付，3取消支付
     */
    @ApiModelProperty(value = "支付状态", example = "1交易成功，2交易失败，3待支付，4取消支付")
    private FundsStatusEnum status;
    /**
     * 交易状态;1待支付2已支付3支付失败
     */
    @ApiModelProperty(value = "交易状态", example = "1待支付2已支付3支付失败")
    private FundsTransactionStatusEnum transactionStatus;
    /**
     * 支付渠道;1支付宝2微信3其他
     */
    @ApiModelProperty(value = "支付渠道", example = "1支付宝2微信3其他")
    private FundsChannelEnum channel;
    /**
     * 交易订单详情描述
     */
    @ApiModelProperty("交易订单详情描述")
    private String description;
}
