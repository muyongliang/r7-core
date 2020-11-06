package com.r7.core.uim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.uim.dto.UimOauthOrderDTO;
import com.r7.core.uim.dto.UimOauthOrderUpdateDTO;
import com.r7.core.uim.vo.UimOauthOrderVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户认证订单表
 *
 * @author zhongpingli
 */
@Data
@ApiModel(description = "用户认证订单表")
@TableName("uim_oauth_order")
@EqualsAndHashCode(callSuper = true)
public class UimOauthOrder extends Model<UimOauthOrder> {

    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 平台ID
     */
    @ApiModelProperty("平台ID")
    private Long appId;
    /**
     * 认证类型;实名认证，视频认证，电子签认证
     */
    @ApiModelProperty("认证类型")
    private Integer type;
    /**
     * 认证项目key;比如实名认证中，身份证人头面对应的key，或者国徽面对应的key，审核内容对应的key
     */
    @ApiModelProperty("认证项目key")
    private String oauthKey;
    /**
     * 审核内容;统一使用json格式存储
     */
    @ApiModelProperty("审核内容")
    private String context;
    /**
     * 审核状态 1未审核，2待审核，3审核通过，4审核驳回
     */
    @ApiModelProperty("审核状态")
    private Integer status;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
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

    public UimOauthOrderVO toUimOauthOrderVo() {
        UimOauthOrderVO uimOauthOrderVo = new UimOauthOrderVO();
        uimOauthOrderVo.setId(this.getId());
        uimOauthOrderVo.setUserId(this.getUserId());
        uimOauthOrderVo.setAppId(this.getAppId());
        uimOauthOrderVo.setType(this.getType());
        uimOauthOrderVo.setOauthKey(this.getOauthKey());
        uimOauthOrderVo.setContext(this.getContext());
        uimOauthOrderVo.setStatus(this.getStatus());
        uimOauthOrderVo.setSort(this.getSort());
        uimOauthOrderVo.setDescription(this.getDescription());
        return uimOauthOrderVo;
    }

    public void toUimOauthOrder(UimOauthOrderDTO uimOauthOrderDto) {
        this.setUserId(uimOauthOrderDto.getUserId());
        this.setType(uimOauthOrderDto.getType());
        this.setOauthKey(uimOauthOrderDto.getOauthKey());
        this.setContext(uimOauthOrderDto.getContext());
        this.setStatus(uimOauthOrderDto.getStatus());
        this.setSort(uimOauthOrderDto.getSort());
        this.setDescription(uimOauthOrderDto.getDescription());
    }

    public void toUimOauthOrder(UimOauthOrderUpdateDTO uimOauthOrderUpdateDto) {
        this.setContext(uimOauthOrderUpdateDto.getContext());
        this.setStatus(uimOauthOrderUpdateDto.getStatus());
        this.setDescription(uimOauthOrderUpdateDto.getDescription());
    }
}
