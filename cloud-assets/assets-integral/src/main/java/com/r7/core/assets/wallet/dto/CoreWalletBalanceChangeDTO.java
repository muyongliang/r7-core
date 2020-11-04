package com.r7.core.assets.wallet.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author: zs
 * @description: 钱包修改传输实体
 * @date: 2020-10-30
 **/
@Data
@ApiModel("钱包修改传输实体")
public class CoreWalletBalanceChangeDTO {
    /**
     * 钱包支付密码;6位数的支付密码
     */
    @ApiModelProperty(value = "钱包支付密码", example = "123456")
    @NotBlank(message = "钱包密码不能为空")
    private String payPassword;
    /**
     * 钱包总余额
     */
    @ApiModelProperty("钱包总余额")
    @NotNull(message = "钱包总余额不能为空")
    private Integer balance;
}
