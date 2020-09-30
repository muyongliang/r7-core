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
 * 角色资源关联
 *
 * @author zhongpingli
 */
@Data
@ApiModel(description = "角色资源关联")
@TableName("uim_role_resource")
@EqualsAndHashCode(callSuper = true)
public class UimRoleResource extends Model<UimRoleResource> {

    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 资源ID
     */
    @ApiModelProperty("资源ID")
    private Long resourceId;
    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private Long roleId;
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
