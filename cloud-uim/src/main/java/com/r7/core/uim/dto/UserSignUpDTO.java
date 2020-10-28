package com.r7.core.uim.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用户注册信息
 *
 * @author zhongpingli
 */
@Data
@ApiModel("用户注册")
public class UserSignUpDTO {

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空")
    @ApiModelProperty("昵称")
    private String userName;
    /**
     * 电话
     */
    @NotNull(message = "电话不能为空")
    @ApiModelProperty("电话")
    private Long phoneNumber;

    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty("验证码")
    private String code;

    /**
     * 账户密码
     */
    @NotEmpty(message = "账户密码不能为空")
    @ApiModelProperty("账户密码")
    private String password;

}
