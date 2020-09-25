package com.r7.core.uim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@TableName("uim_organ")
@ApiModel(description = "组织")
@EqualsAndHashCode(callSuper = true)
public class UimOrgan extends Model<UimOrgan> {

    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 父组织
     */
    @ApiModelProperty("父id")
    private Long pId;
    /**
     * 组织编码
     */
    @ApiModelProperty("组织编码")
    private String organCode;
    /**
     * 组织名称
     */
    @ApiModelProperty("组织名称")
    private String organName;
    /**
     * 类型;1组织0部门
     */
    @ApiModelProperty(value = "组织类型", example = "1")
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
