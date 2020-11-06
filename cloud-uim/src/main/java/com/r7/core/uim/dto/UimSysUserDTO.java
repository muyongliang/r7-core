package com.r7.core.uim.dto;

import com.r7.core.uim.constant.UimSysUserDelEnum;
import com.r7.core.uim.constant.UimSysUserStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wt
 * @Description 新增系统用户传输层
 */
@Data
@ApiModel("新增系统用户传输层")
public class UimSysUserDTO {

    /**
     * 部门id
     */
    @NotNull(message = "部门id不能为空")
    @ApiModelProperty("部门id")
    private Long branchId;
    /**
     * 登录名
     */
    @NotBlank(message = "登录名不能为空")
    @ApiModelProperty("登录名")
    private String loginName;
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    @NotBlank(message = "昵称不能为空")
    private String userName;
    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar ;
    /**
     * 电话
     */
    @ApiModelProperty("电话")
    @NotBlank(message = "电话不能为空")
    private String phoneNumber;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * 账户密码
     */
    @ApiModelProperty("账户密码")
    @NotBlank(message = "账户密码不能为空")
    private String password;



}
