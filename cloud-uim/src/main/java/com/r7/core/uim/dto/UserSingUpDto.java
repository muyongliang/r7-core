package com.r7.core.uim.dto;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户注册信息
 *
 * @author zhongpingli
 */
public class UserSingUpDto {

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
     * 电话
     */
    private String phoneNumber;

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


}
