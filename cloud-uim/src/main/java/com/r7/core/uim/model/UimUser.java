package com.r7.core.uim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@TableName("uim_user")
@EqualsAndHashCode(callSuper = true)
public class UimUser extends Model<UimUser> {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 组织ID
     */
    private Long organId;
    /**
     * 邀请码
     */
    private String code;
    /**
     * 昵称
     */
    private String userName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 电话
     */
    private String phoneNumber;
    /**
     * 是否认证 1未认证，2认证
     */
    private Integer isOauth;
    /**
     * 状态;1正常，2冻结
     */
    private Integer status;
    /**
     * 账户密码
     */
    private String password;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 删除;1未删除，2删除(注销)
     */
    private Integer del;
    /**
     * 创建人
     */
    private Long createdBy;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 更新人
     */
    private Long updatedBy;
    /**
     * 更新时间
     */
    private Date updatedAt;


}
