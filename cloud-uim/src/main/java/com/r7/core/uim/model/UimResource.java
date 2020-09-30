package com.r7.core.uim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.uim.dto.UimResourceSaveDto;
import com.r7.core.uim.dto.UimResourceUpdateDto;
import com.r7.core.uim.vo.UimResourceNodeVo;
import com.r7.core.uim.vo.UimResourceVo;
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


    public void toUimResource(UimResourceSaveDto uimResourceSaveDto) {
        this.setPId(uimResourceSaveDto.getPId());
        this.setCode(uimResourceSaveDto.getCode());
        this.setResourceName(uimResourceSaveDto.getResourceName());
        this.setUrl(uimResourceSaveDto.getUrl());
        this.setType(uimResourceSaveDto.getType());
        this.setSort(uimResourceSaveDto.getSort());
    }

    public void toUimResource(UimResourceUpdateDto uimResourceUpdateDto) {
        this.setCode(uimResourceUpdateDto.getCode());
        this.setResourceName(uimResourceUpdateDto.getResourceName());
        this.setUrl(uimResourceUpdateDto.getUrl());
        this.setType(uimResourceUpdateDto.getType());
        this.setSort(uimResourceUpdateDto.getSort());
    }

    public UimResourceVo toUimResourceVo() {
        UimResourceVo uimResourceVo = new UimResourceVo();
        uimResourceVo.setId(this.getId());
        uimResourceVo.setCode(this.getCode());
        uimResourceVo.setPId(this.getPId());
        uimResourceVo.setResourceName(this.getResourceName());
        uimResourceVo.setSort(this.getSort());
        uimResourceVo.setType(this.getType());
        uimResourceVo.setUrl(this.getUrl());
        return uimResourceVo;
    }

    public UimResourceNodeVo toUimResourceNodeVo() {
        UimResourceNodeVo uimResourceNodeVo = new UimResourceNodeVo();
        uimResourceNodeVo.setId(this.getId());
        uimResourceNodeVo.setPId(this.getPId());
        uimResourceNodeVo.setCode(this.getCode());
        uimResourceNodeVo.setResourceName(this.getResourceName());
        uimResourceNodeVo.setUrl(this.getUrl());
        uimResourceNodeVo.setType(this.getType());
        return uimResourceNodeVo;
    }

}
