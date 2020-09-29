package com.r7.core.uim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 资源表
 *
 * @author zhongpingli
 */
@Data
@ApiModel(description = "资源表")
@TableName("uim_resource")
@EqualsAndHashCode(callSuper = true)
public class UimResource extends Model<UimResource> {

    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 资源父类
     */
    @ApiModelProperty("资源父类")
    private Long pId;
    /**
     * 平台id
     */
    @ApiModelProperty("平台id")
    private Long appId;
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
    private Integer type;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
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

}
