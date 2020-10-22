package com.r7.core.uim.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户信息
 *
 * @author zhongpingli
 */
@Data
@ApiModel("用户信息")
public class UimUserVO {

    @ApiModelProperty("id")
    private Long id;

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
    private Long phoneNumber;

}
