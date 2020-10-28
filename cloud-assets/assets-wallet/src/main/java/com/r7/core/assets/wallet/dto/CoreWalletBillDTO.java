package com.r7.core.assets.wallet.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zs
 * @description: 钱包账单传输实体
 * @date : 2020-10-26
 */
@Data
@ApiModel(description = "钱包账单传输层")
public class CoreWalletBillDTO {
    /** 用户id */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId ;
    /** 交易用户id */
    @ApiModelProperty("交易用户id")
    @NotNull(message = "交易用户id不能为空")
    private Long transactionUserId ;
    /** 业务单号;购买订单号 */
    @ApiModelProperty("业务单号;购买订单号")
    @NotBlank(message = "业务单号不能为空")
    private String businessSn ;
    /** 是否展示;1不展示2展示3后台展示所有 */
    @ApiModelProperty(value = "是否展示", example = "1不展示2展示3后台展示所有")
    @NotNull(message = "是否展示不能为空")
    private Integer isShow ;
    /** 交易金额 */
    @ApiModelProperty("交易金额 ")
    @NotNull(message = "交易金额不能为空")
    private Integer transactionAmount ;
    /** 交易类型;收入1/支出2/冻结3/解冻4 */
    @ApiModelProperty(value = "交易类型",example = "收入1/支出2/冻结3/解冻4")
    @NotNull(message = "交易类型不能为空")
    private Integer type ;
    /** 交易来源;如购买商品，余额充值，余额提现 */
    @ApiModelProperty(value = "交易来源", example = "如购买商品，余额充值，余额提现")
    @NotBlank(message = "交易来源不能为空")
    private String source ;
    /** 钱包余额 */
    @ApiModelProperty("钱包余额")
    @NotNull(message = "钱包余额不能为空")
    private Integer balance ;
    /** 交易状态;1交易成功/2待支付/3支付失败 */
    @ApiModelProperty(value = "交易状态", example = "1交易成功/2待支付/3支付失败")
    @NotNull(message = "交易状态不能为空")
    private Integer status ;
    /** 钱包余额交易描述;如：股票转账、充值、买文章、文章收费、积分购买、权益商品卖出等 */
    @ApiModelProperty(value = "钱包余额交易描述", example = "如：股票转账、充值、买文章、文章收费、积分购买、权益商品卖出等")
    @NotBlank(message = "钱包交易描述不能为空")
    private String description ;
}
