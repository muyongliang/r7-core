package com.r7.core.uim.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 
 * @Description 平台信息表传输层
 * @author wt
 * 
 */
@Data
@ApiModel(value="平台信息传输层")

public class CorePlatformDTO {

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