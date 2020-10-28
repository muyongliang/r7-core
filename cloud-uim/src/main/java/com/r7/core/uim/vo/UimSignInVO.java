package com.r7.core.uim.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录返回信息
 *
 * @author zhongpingli
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel("登录返回")
public class UimSignInVO {
    /**
     * 访问令牌
     */
    @ApiModelProperty("访问令牌")
    private String token;
    /**
     * 刷新令牌
     */
    @ApiModelProperty("刷新令牌")
    private String refreshToken;
    /**
     * 访问令牌头前缀
     */
    @ApiModelProperty("访问令牌头前缀")
    private String tokenHead;
    /**
     * 有效时间（秒）
     */
    @ApiModelProperty("有效时间（秒）")
    private Integer expiresIn;


}
