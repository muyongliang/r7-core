package com.r7.core.assets.funds.vo;

import com.r7.core.assets.funds.constant.FundsTransactionStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: zs
 * @description 分页展示资金流动记录
 * @date: 2020-10-30
 **/
@Data
@ApiModel("分页展示资金流动记录")
public class CoreFundsPageVO {
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
     * 交易状态;1待支付2已支付3支付失败
     */
    @ApiModelProperty(value = "交易状态", example = "1待支付2已支付3支付失败")
    private FundsTransactionStatusEnum transactionStatus;
    /**
     * 创建时间;充值时间
     */
    @ApiModelProperty("创建时间,充值时间")
    private Date createdAt;
}
