package com.r7.core.uim.dto;

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
    @NotBlank(message = "头像不能为空")
    private String avatar;
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
     * 状态;0正常，1冻结，2注销
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态", example = "0")
    private Integer status;
    /**
     * 账户密码
     */
    @ApiModelProperty("账户密码")
    @NotBlank(message = "账户密码不能为空")
    private String password;

    /**
     * 删除;1未删除，2删除(注销)
     */
    @NotNull(message = "删除状态不能为空")
    @ApiModelProperty("删除状态")
    private Integer del;


}
