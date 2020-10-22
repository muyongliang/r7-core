package com.r7.core.uim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.uim.dto.UimOrganSaveDTO;
import com.r7.core.uim.dto.UimOrganUpdateDTO;
import com.r7.core.uim.vo.UimOrganNodeVO;
import com.r7.core.uim.vo.UimOrganVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 组织
 *
 * @author zhongpingli
 */
@Data
@TableName("uim_organ")
@ApiModel(description = "组织")
@EqualsAndHashCode(callSuper = true)
public class UimOrgan extends Model<UimOrgan> {

    @TableId
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("平台ID")
    private Long appId;
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


    public void toUimOrgan(UimOrganSaveDTO uimOrganSaveDTO) {
        this.setOrganCode(uimOrganSaveDTO.getOrganCode());
        this.setOrganName(uimOrganSaveDTO.getOrganName());
        this.setType(uimOrganSaveDTO.getType());
        this.setSort(uimOrganSaveDTO.getSort());
    }

    public void toUimOrgan(UimOrganUpdateDTO uimOrganUpdateDTO) {
        this.setPId(uimOrganUpdateDTO.getPId());
        this.setOrganCode(uimOrganUpdateDTO.getOrganCode());
        this.setOrganName(uimOrganUpdateDTO.getOrganName());
        this.setType(uimOrganUpdateDTO.getType());
        this.setSort(uimOrganUpdateDTO.getSort());
    }

    public UimOrganVO toUimOrganVO() {
        UimOrganVO uimOrganVO = new UimOrganVO();
        uimOrganVO.setId(this.getId());
        uimOrganVO.setPId(this.getPId());
        uimOrganVO.setOrganCode(this.getOrganCode());
        uimOrganVO.setOrganName(this.getOrganName());
        uimOrganVO.setType(this.getType());
        uimOrganVO.setSort(this.getSort());
        return uimOrganVO;
    }

    public UimOrganNodeVO toUimOrganNodeVO() {
        UimOrganNodeVO uimOrganNodeVO = new UimOrganNodeVO();
        uimOrganNodeVO.setId(this.id);
        uimOrganNodeVO.setPId(this.pId);
        uimOrganNodeVO.setOrganCode(this.organCode);
        uimOrganNodeVO.setOrganName(this.organName);
        uimOrganNodeVO.setType(this.type);
        return uimOrganNodeVO;
    }

}
