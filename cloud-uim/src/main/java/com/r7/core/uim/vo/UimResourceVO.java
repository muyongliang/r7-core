package com.r7.core.uim.vo;


import com.r7.core.uim.constant.ResourceEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源展示
 *
 * @author zhongpingli
 */
@Data
@ApiModel("资源展示")
public class UimResourceVO {

    @ApiModelProperty("id")
    private Long id;
    /**
     * 资源父类
     */
    @ApiModelProperty("资源父类")
    private Long pId;
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
