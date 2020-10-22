package com.r7.core.uim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户修改信息传输实体
 *
 * @author zhongpingli
 */
@Data
@ApiModel("用户修改信息传输实体")
public class UimUserUpdateDTO {

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空")
    @ApiModelProperty("昵称")
    private String userName;
    /**
     * 头像
     */
    @NotEmpty(message = "头像不能为空")
    @ApiModelProperty("头像")
    private String avatar;

}
