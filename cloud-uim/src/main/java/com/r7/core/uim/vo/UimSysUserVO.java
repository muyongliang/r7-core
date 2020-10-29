package com.r7.core.uim.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wt
 * @Description 系统用户视图
 */
@Data
@ApiModel("系统用户视图")
public class UimSysUserVO {


    @TableId
    @ApiModelProperty("ID")
    private Long id;
    /**
     * 平台id
     */
    @ApiModelProperty("平台id")
    private Long appId;
    /**
     * 组织ID
     */
    @ApiModelProperty("组织ID")
    private Long organId;
    /**
     * 部门id
     */
    @ApiModelProperty("部门ID")
    private Long branchId;
    /**
     * 登录名
     */
    @ApiModelProperty("登录名")
    private String loginName;
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String userName;
    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;
    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String phoneNumber;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;
    /**
     * 状态;0正常，1冻结，2注销
     */
    @ApiModelProperty(value = "状态", example = "0")
    private Integer status;
    /**
     * 账户密码
     */
    @ApiModelProperty("账户密码")
    private String password;

    /**
     * 删除;1未删除，2删除(注销)
     */
    @ApiModelProperty("删除状态")
    private Integer del;

    /**
     * IP地址
     */
    @ApiModelProperty("IP地址")
    private String ip;

    /**
     * 邀请码
     */
    @ApiModelProperty("邀请码")
    private String code;
}
