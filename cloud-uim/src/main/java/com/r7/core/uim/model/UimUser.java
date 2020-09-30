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
 * 用户表
 *
 * @author zhongpingli
 */
@Data
@ApiModel(description = "用户表")
@TableName("uim_user")
@EqualsAndHashCode(callSuper = true)
public class UimUser extends Model<UimUser> {

    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 组织ID
     */
    @ApiModelProperty("组织ID")
    private Long organId;
    /**
     * 邀请码
     */
    @ApiModelProperty("邀请码")
    private String code;
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
     * 是否认证
     */
    @ApiModelProperty("是否认证")
    private Integer isOauth;
    /**
     * 状态;1正常，2冻结
     */
    @ApiModelProperty("状态")
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
     * 删除;1未删除，2删除(注销)
     */
    @ApiModelProperty("删除状态")
    private Integer del;
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
