package com.r7.core.uim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.uim.dto.UimOauthInfoDTO;
import com.r7.core.uim.vo.UimOauthInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 认证信息表
 *
 * @author zhongpingli
 */
@Data
@ApiModel(description = "认证信息表")
@TableName("uim_oauth_info")
@EqualsAndHashCode(callSuper = true)
public class UimOauthInfo extends Model<UimOauthInfo> {

    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 真实名称
     */
    @ApiModelProperty("真实名称")
    private String oauthName;
    /**
     * 身份证
     */
    @ApiModelProperty("身份证")
    private String identity;
    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private Integer sex;
    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    private Integer age;
    /**
     * 出生年月日;yyyyMMdd
     */
    @ApiModelProperty("出生年月日")
    private Integer birthday;
    /**
     * 籍贯;身份证的地址
     */
    @ApiModelProperty("籍贯")
    private String nativePlace;
    /**
     * 省
     */
    @ApiModelProperty("省")
    private String province;
    /**
     * 市;直辖市属于省级市保存在省中
     */
    @ApiModelProperty("市")
    private String city;
    /**
     * 县(区)
     */
    @ApiModelProperty("县")
    private String county;
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

    public void toOauthInfo(UimOauthInfoDTO uimOauthInfoDto) {
        this.setOauthName(uimOauthInfoDto.getOauthName());
        this.setIdentity(uimOauthInfoDto.getIdentity());
        this.setSex(uimOauthInfoDto.getSex());
        this.setAge(uimOauthInfoDto.getAge());
        this.setBirthday(uimOauthInfoDto.getBirthday());
        this.setNativePlace(uimOauthInfoDto.getNativePlace());
        this.setProvince(uimOauthInfoDto.getProvince());
        this.setCity(uimOauthInfoDto.getCity());
        this.setCounty(uimOauthInfoDto.getCounty());
    }

    public UimOauthInfoVO toUimOauthInfoVo() {
        UimOauthInfoVO uimOauthInfoVo = new UimOauthInfoVO();
        uimOauthInfoVo.setId(this.getId());
        uimOauthInfoVo.setUserId(this.getUserId());
        uimOauthInfoVo.setOauthName(this.getOauthName());
        uimOauthInfoVo.setIdentity(this.getIdentity());
        uimOauthInfoVo.setSex(this.getSex());
        uimOauthInfoVo.setAge(this.getAge());
        uimOauthInfoVo.setBirthday(this.getBirthday());
        uimOauthInfoVo.setNativePlace(this.getNativePlace());
        uimOauthInfoVo.setProvince(this.getProvince());
        uimOauthInfoVo.setCity(this.getCity());
        uimOauthInfoVo.setCounty(this.getCounty());
        return uimOauthInfoVo;
    }
}
