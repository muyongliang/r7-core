package com.r7.core.uim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.uim.dto.UimSysUserDTO;
import com.r7.core.uim.dto.UimSysUserUpdateDTO;
import com.r7.core.uim.vo.UimSysUserVO;
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
    @ApiModelProperty("部门id")
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

    public void toUimSysUserUpdateDTO(UimSysUserUpdateDTO uimSysUserUpdateDTO){
        this.setLoginName(uimSysUserUpdateDTO.getLoginName());
        this.setUserName(uimSysUserUpdateDTO.getUserName());
        this.setAvatar(uimSysUserUpdateDTO.getAvatar());
        this.setPhoneNumber(uimSysUserUpdateDTO.getPhoneNumber());
        this.setEmail(uimSysUserUpdateDTO.getEmail());
        this.setStatus(uimSysUserUpdateDTO.getStatus());
        this.setPassword(uimSysUserUpdateDTO.getPassword());

    }
    public void toUimSysUserDTO(UimSysUserDTO uimSysUserDTO){
        this.setBranchId(uimSysUserDTO.getBranchId());
        this.setLoginName(uimSysUserDTO.getLoginName());
        this.setUserName(uimSysUserDTO.getUserName());
        this.setAvatar(uimSysUserDTO.getAvatar());
        this.setPhoneNumber(uimSysUserDTO.getPhoneNumber());
        this.setEmail(uimSysUserDTO.getEmail());
        this.setStatus(uimSysUserDTO.getStatus());
        this.setPassword(uimSysUserDTO.getPassword());
    }

    public UimSysUserVO toUimSysUserVO(){
        UimSysUserVO uimSysUserVO = new UimSysUserVO();
        uimSysUserVO.setId(this.getId());
        uimSysUserVO.setAppId(this.getAppId());
        uimSysUserVO.setOrganId(this.getOrganId());
        uimSysUserVO.setBranchId(this.getBranchId());
        uimSysUserVO.setLoginName(this.getLoginName());
        uimSysUserVO.setUserName(this.getUserName());
        uimSysUserVO.setAvatar(this.getAvatar());
        uimSysUserVO.setPhoneNumber(this.getPhoneNumber());
        uimSysUserVO.setEmail(this.getEmail());
        uimSysUserVO.setStatus(this.getStatus());
        uimSysUserVO.setPassword(this.getPassword());
        uimSysUserVO.setIp(this.getIp());
        return uimSysUserVO;
    }


}
