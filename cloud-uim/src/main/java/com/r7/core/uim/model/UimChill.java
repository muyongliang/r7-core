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
 * 冻结表
 *
 * @author zhongpingli
 */
@Data
@ApiModel(description = "冻结记录表")
@TableName("uim_chill")
@EqualsAndHashCode(callSuper = true)
public class UimChill extends Model<UimChill> {

    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 冻结用户
     */
    @ApiModelProperty("冻结用户")
    private Long userId;
    /**
     * 冻结资源;登录/邀请码/提现/平台冻结
     */
    @ApiModelProperty("冻结资源id")
    private String resourceId;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
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
