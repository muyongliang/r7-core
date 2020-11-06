package com.r7.core.uim.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @Description 平台信息表视图
 * @author wt
 * 
 */
@Data
@ApiModel(value="平台信息视图")

public class CorePlatformVO {


    /**
     * id
     */
    @TableId(value = "id")
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 平台名称
     */
    @ApiModelProperty(value="平台名称")
    private String appName;

    /**
     * 平台描述
     */
    @ApiModelProperty(value="平台描述")
    private String description;


}