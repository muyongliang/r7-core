package com.r7.core.uim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.uim.dto.UimOauthDTO;
import com.r7.core.uim.vo.UimOauthVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户认证表
 *
 * @author zhongpingli
 */
@Data
@ApiModel(description = "用户认证表")
@TableName("uim_oauth")
@EqualsAndHashCode(callSuper = true)
public class UimOauth extends Model<UimOauth> {

    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;
    /**
     * 平台ID
     */
    @ApiModelProperty("平台ID")
    private Long appId;
    /**
     * 认证订单id
     */
    @ApiModelProperty("认证订单id")
    private Long oauthOrderId;
    /**
     * 认证类型 实名认证，视频认证，电子签认证
     */
    @ApiModelProperty("认证类型")
    private Integer type;
    /**
     * 认证状态
     */
    @ApiModelProperty("认证状态")
    private Integer status;
    /**
     * 驳回原因
     */
    @ApiModelProperty("驳回原因")
    private String reason;
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


    public void toUimOauth(UimOauthDTO uimOauthDto) {
        this.setUserId(uimOauthDto.getUserId());
        this.setOauthOrderId(uimOauthDto.getOauthOrderId());
        this.setType(uimOauthDto.getType());
        this.setStatus(uimOauthDto.getStatus());
        this.setReason(uimOauthDto.getReason());
    }

    public UimOauthVO toUimOauthVo() {
        UimOauthVO uimOauthVo = new UimOauthVO();
        uimOauthVo.setId(this.getId());
        uimOauthVo.setUserId(this.getUserId());
        uimOauthVo.setAppId(this.getAppId());
        uimOauthVo.setOauthOrderId(this.getOauthOrderId());
        uimOauthVo.setType(this.getType());
        uimOauthVo.setStatus(this.getStatus());
        uimOauthVo.setReason(this.getReason());
        return uimOauthVo;
    }
}
