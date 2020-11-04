package com.r7.core.assets.wallet.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: zs
 * @description: 钱包不可用余额传输实体
 * @date: 2020-10-31
 **/
@Data
@ApiModel("钱包不可用余额传输实体")
public class CoreWalletLockingBalanceChangeDTO {
    /**
     * 钱包支付密码;6位数的支付密码
     */
    @ApiModelProperty(value = "钱包支付密码", example = "123456")
    @NotBlank(message = "钱包支付密码不能为空")
    private String payPassword;
    /**
     * 不可用余额;不能提现的余额，权益仓库保管费类似押金
     */
    @ApiModelProperty("不可用余额")
    @NotNull(message = "钱包不可用余额不能为空")
    private Integer lockingBalance;
}
