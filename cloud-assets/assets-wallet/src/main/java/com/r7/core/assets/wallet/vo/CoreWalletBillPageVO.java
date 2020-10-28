package com.r7.core.assets.wallet.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zs
 * @description: 钱包账单分页展示
 * @date : 2020-10-26
 */
@Data
@ApiModel("钱包账单分页展示视图")
public class CoreWalletBillPageVO {
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
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 交易组织id
     */
    @ApiModelProperty("交易组织id")
    private Long organId;
    /**
     * 交易用户id
     */
    @ApiModelProperty("交易用户id")
    private Long transactionUserId;
    /**
     * 交易金额
     */
    @ApiModelProperty("交易金额")
    private Integer transactionAmount;
    /**
     * 交易类型;收入1/支出2/冻结3/解冻4
     */
    @ApiModelProperty(value = "交易类型", example = "收入1/支出2/冻结3/解冻4")
    private Integer type;
    /**
     * 交易来源;如购买商品，余额充值，余额提现
     */
    @ApiModelProperty(value = "交易来源", example = "如购买商品，余额充值，余额提现")
    private String source;
    /**
     * 交易状态;1交易成功/2待支付/3支付失败
     */
    @ApiModelProperty(value = "交易状态", example = "1交易成功/2待支付/3支付失败")
    private Integer status;
    /**
     * 钱包余额交易描述;如：股票转账、充值、买文章、文章收费、积分购买、权益商品卖出等
     */
    @ApiModelProperty(value = "钱包余额交易描述", example = "如：股票转账、充值、买文章、文章收费、积分购买、权益商品卖出等")
    private String description;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createdAt;
}
