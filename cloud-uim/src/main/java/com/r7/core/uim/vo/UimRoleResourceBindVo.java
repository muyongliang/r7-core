package com.r7.core.uim.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色资源绑定
 *
 * @author zhongpingli
 */
@Data
@ApiModel("角色资源绑定")
public class UimRoleResourceBindVo {

    @ApiModelProperty("角色")
    private Long roleId;

    @ApiModelProperty("资源ID")
    private Long resourceId;

    @ApiModelProperty("资源编码")
    private String resourceCode;

    @ApiModelProperty("资源名称")
    private String resourceName;

    @ApiModelProperty("资源地址")
    private String resourceUrl;

}
