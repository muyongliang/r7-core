package com.r7.core.uim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.uim.dto.CorePlatformDTO;
import com.r7.core.uim.vo.CorePlatformVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 
 * @Description 平台信息表实体
 * @author wt
 * 
 */
@Data
@ApiModel(value="平台信息表")
@TableName(value = "core_platform")
@EqualsAndHashCode(callSuper = true)
public class CorePlatform  extends Model<CorePlatform> {
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

    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createdAt;

    /**
     * 更新人
     */
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private LocalDateTime updatedAt;

    public void toCorePlatformDto(CorePlatformDTO corePlatformDto){
        this.setAppName(corePlatformDto.getAppName());
        this.setDescription(corePlatformDto.getDescription());
    }

    public CorePlatformVO toCorePlatformVO(){
        CorePlatformVO corePlatformVO =new CorePlatformVO();
        corePlatformVO.setId(this.getId());
        corePlatformVO.setAppName(this.getAppName());
        corePlatformVO.setDescription(this.getDescription());
        return corePlatformVO;
    }
}