package com.r7.core.assets.wallet.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zs
 * @description: 钱包分页展示
 * @date : 2020-10-27
 */
@ApiModel("钱包分页展示视图")
public class CoreWalletPageVO {
    /**
     * id
     */
    @ApiModelProperty("id")
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 钱包总余额
     */
    @ApiModelProperty("钱包总余额")
    private Integer balance;
    /**
     * 不可用余额;不能提现的余额，权益仓库保管费类似押金
     */
    @ApiModelProperty("不可用余额")
    private Integer lockingBalance;
    /**
     * 签名;用户ID，钱包支付密码，钱包余额三者进行加密，增减时从数据库中
     */
    @ApiModelProperty("签名")
    private String sign;
}
