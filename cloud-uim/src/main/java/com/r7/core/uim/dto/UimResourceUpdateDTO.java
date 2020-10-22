package com.r7.core.uim.dto;

import com.r7.core.uim.constant.PermissionEnum;
import com.r7.core.uim.constant.ResourceEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源修改传输
 *
 * @author zhongpingli
 */
@Data
@ApiModel("资源修改传输")
public class UimResourceUpdateDTO {

    /**
     * 资源标识
     */
    @ApiModelProperty("资源标识")
    private String code;
    /**
     * 资源名称
     */
    @ApiModelProperty("资源名称")
    private String resourceName;
    /**
     * 资源地址
     */
    @ApiModelProperty("资源地址")
    private String url;

    @ApiModelProperty("权限:browse、create、update、delete四个之一")
    private PermissionEnum permission;
    /**
     * 资源类型;0菜单/1按钮
     */
    @ApiModelProperty("资源类型")
    private ResourceEnum type;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
}
