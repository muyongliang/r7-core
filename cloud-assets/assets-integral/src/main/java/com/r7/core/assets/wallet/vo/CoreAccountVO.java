package com.r7.core.assets.wallet.vo;

import com.r7.core.assets.wallet.constant.AccountChannelEnum;
import com.r7.core.assets.wallet.constant.AccountTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zs
 * @description: 用户账号展示实体
 * @date : 2020-10-29
 */
@Data
@ApiModel("用户账号展示实体")
public class CoreAccountVO {
    @ApiModelProperty("id")
    private Long id;
    /**
     * 平台id
     */
    @ApiModelProperty("平台id")
    private Long appId;
    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organId;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 用户账户
     */
    @ApiModelProperty("用户账户")
    private String account;
    /**
     * 提现渠道;1支付宝；2微信；3银联；4股票；5其他
     */
    @ApiModelProperty(value = "提现渠道", example = "1支付宝；2微信；3银联；4股票；5其他")
    private AccountChannelEnum channel;
    /**
     * 类型;1提现账号；2转账账号；3其它
     */
    @ApiModelProperty(value = "类型", example = "1提现账号；2转账账号；3其它")
    private AccountTypeEnum type;
}
