package com.r7.core.uim.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zs
 * @description:
 * @date : 2020-10-14
 */
@Data
@ApiModel("用户认证信息展示")
public class UimOauthInfoVO {
    /**
     * id
     */
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
}
