package com.r7.core.uim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.uim.dto.UimRoleDTO;
import com.r7.core.uim.dto.UimRoleSaveDTO;
import com.r7.core.uim.vo.UimRoleVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 角色信息
 *
 * @author zhongpingli
 */
@Data
@TableName("uim_role")
@ApiModel(description = "角色信息")
@EqualsAndHashCode(callSuper = true)
public class UimRole extends Model<UimRole> {

    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 平台id
     */
    @ApiModelProperty("平台id")
    private Long appId;
    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organId;
    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String code;
    /**
     * 角色名
     */
    @ApiModelProperty("角色名")
    private String roleName;
    /**
     * 特殊角色;特定角色不展示给后端
     */
    @ApiModelProperty("特殊角色")
    private Integer feature;
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

    public UimRoleVO toUimRoleVo() {
        UimRoleVO uimRoleVo = new UimRoleVO();
        uimRoleVo.setId(this.getId());
        uimRoleVo.setCode(this.getCode());
        uimRoleVo.setRoleName(this.getRoleName());
        uimRoleVo.setFeature(this.getFeature());
        uimRoleVo.setSort(this.getSort());
        return uimRoleVo;
    }

    public void toUimRole(UimRoleDTO uimRoleDto) {
        this.setCode(uimRoleDto.getCode());
        this.setRoleName(uimRoleDto.getRoleName());
        this.setFeature(uimRoleDto.getFeature());
        this.setSort(uimRoleDto.getSort());
    }

    public void toUimRole(UimRoleSaveDTO uimRoleSaveDto) {
        this.setCode(uimRoleSaveDto.getCode());
        this.setRoleName(uimRoleSaveDto.getRoleName());
        this.setFeature(0);
        this.setSort(uimRoleSaveDto.getSort());
    }

}
