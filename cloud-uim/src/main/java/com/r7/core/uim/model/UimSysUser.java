package com.r7.core.uim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统用户信息
 *
 * @author zhongpingli
 */
@Data
@TableName("uim_sys_user")
@ApiModel(description = "系统用户表")
@EqualsAndHashCode(callSuper = true)
public class UimSysUser extends Model<UimSysUser> {

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
    @ApiModelProperty("组织ID")
    private Long branchId;
    /**
     * 登录名
     */
    @ApiModelProperty("组织ID")
    private String loginName;
    /**
     * 昵称
     */
    @ApiModelProperty("组织ID")
    private String userName;
    /**
     * 头像
     */
    @ApiModelProperty("组织ID")
    private String avatar;
    /**
     * 电话
     */
    @ApiModelProperty("组织ID")
    private String phoneNumber;
    /**
     * 邮箱
     */
    @ApiModelProperty("组织ID")
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
     * IP地址
     */
    @ApiModelProperty("IP地址")
    private String ip;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Long createdBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createdAt;
    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private Long updatedBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updatedAt;

}
