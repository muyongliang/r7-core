package com.r7.core.assets.wallet.dto;

import com.r7.core.assets.wallet.constant.AccountChannelEnum;
import com.r7.core.assets.wallet.constant.AccountTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author zs
 * @description: 用户账户传输实体
 * @date : 2020-10-29
 */
@Data
@ApiModel("用户账户传输实体")
public class CoreAccountDTO {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;
    /**
     * 用户账户
     */
    @ApiModelProperty("用户账户")
    @NotBlank(message = "用户账户不能为空")
    private String account;
    /**
     * 提现渠道;1支付宝；2微信；3银联；4股票；5其他
     */
    @ApiModelProperty(value = "提现渠道", example = "1支付宝；2微信；3银联；4股票；5其他")
    @NotNull(message = "提现渠道不能为空")
    private AccountChannelEnum channel;
    /**
     * 类型;1提现账号；2转账账号；3其它
     */
    @ApiModelProperty(value = "类型", example = "1提现账号；2转账账号；3其它")
    @NotNull(message = "类型不能为空")
    private AccountTypeEnum type;
}
