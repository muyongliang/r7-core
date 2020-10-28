package com.r7.core.assets.wallet.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zs
 * @description:
 * @date : 2020-10-27
 */
@Data
public class CoreWalletUpdateDTO {
    /**
     * 钱包支付密码;6位数的支付密码
     */
    @ApiModelProperty(value = "钱包支付密码", example = "123456")
    @NotBlank(message = "钱包支付密码不能为空")
    private String payPassword;
    /**
     * 钱包总余额
     */
    @ApiModelProperty("钱包总余额")
    @NotNull(message = "钱包总余额不能为空")
    private Integer balance;
    /**
     * 不可用余额;不能提现的余额，权益仓库保管费类似押金
     */
    @ApiModelProperty("不可用余额")
    @NotNull(message = "不可用余额不能为空")
    private Integer lockingBalance;
    /**
     * 签名;用户ID，钱包支付密码，钱包余额三者进行加密，增减时从数据库中
     */
    @ApiModelProperty("签名")
    @NotBlank(message = "签名不能为空")
    private String sign;
}
