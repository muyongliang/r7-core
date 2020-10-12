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
public class UserSingUpDTO {

    /**
     * 组织ID
     */
    @NotNull(message = "组织ID不能为空")
    @ApiModelProperty("组织ID")
    private Long organId;

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空")
    @ApiModelProperty("昵称")
    private String userName;
    /**
     * 电话
     */
    @NotEmpty(message = "电话不能为空")
    @ApiModelProperty("电话")
    private String phoneNumber;

    /**
     * 账户密码
     */
    @NotEmpty(message = "账户密码不能为空")
    @ApiModelProperty("账户密码")
    private String password;

}
