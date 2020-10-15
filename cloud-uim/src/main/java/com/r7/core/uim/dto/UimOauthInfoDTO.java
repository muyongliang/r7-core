package com.r7.core.uim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author zs
 * @description:
 * @date : 2020-10-14
 */
@Data
@ApiModel("用户认证信息传输类")
public class UimOauthInfoDTO {
    /**
     * 真实名称
     */
    @ApiModelProperty("真实名称")
    @NotEmpty(message = "真实名称必须存在")
    private String oauthName;
    /**
     * 身份证
     */
    @ApiModelProperty("身份证")
    @NotEmpty(message = "身份证必须存在")
    private String identity;
    /**
     * 性别
     */
    @ApiModelProperty("性别")
    @NotNull(message = "性别不能为空")
    private Integer sex;
    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    @NotNull(message = "年龄不能为空")
    private Integer age;
    /**
     * 出生年月日;yyyyMMdd
     */
    @ApiModelProperty("出生年月日")
    @NotNull(message = "出生年月日不能为空")
    private Integer birthday;
    /**
     * 籍贯;身份证的地址
     */
    @ApiModelProperty("籍贯")
    @NotEmpty(message = "籍贯不能为空")
    private String nativePlace;
    /**
     * 省
     */
    @ApiModelProperty("省")
    @NotEmpty(message = "省份不能为空")
    private String province;
    /**
     * 市;直辖市属于省级市保存在省中
     */
    @ApiModelProperty("市")
    @NotEmpty(message = "市不能为空")
    private String city;
    /**
     * 县(区)
     */
    @ApiModelProperty("县")
    @NotEmpty(message = "县(区)不能为空")
    private String county;
}