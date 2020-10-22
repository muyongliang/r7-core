package com.r7.core.uim.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.uim.dto.UimChillSaveDTO;
import com.r7.core.uim.vo.UimChillVO;
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
    private Long resourceId;
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

    public void toUimChill(UimChillSaveDTO uimChillSaveDto) {
        this.setUserId(uimChillSaveDto.getUserId());
        this.setResourceId(uimChillSaveDto.getResourceId());
        this.setDescription(uimChillSaveDto.getDescription());
    }

    public UimChillVO toUimChillVo() {
        UimChillVO uimChillVO = new UimChillVO();
        uimChillVO.setId(this.getId());
        uimChillVO.setUserId(this.getUserId());
        uimChillVO.setResourceId(this.getResourceId());
        uimChillVO.setDescription(this.getDescription());
        return uimChillVO;
    }
}
