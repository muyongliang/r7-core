package com.r7.core.proxy.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.proxy.dto.CoreProxyDTO;
import com.r7.core.proxy.dto.CoreProxyUpdateDTO;
import com.r7.core.proxy.vo.CoreProxyNodeVO;
import com.r7.core.proxy.vo.CoreProxyVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 *
 * @Description 代理层级信息表实体
 * @author wutao
 *
 */
@ApiModel(description = "代理层级信息实体")
@Data
@TableName(value = "core_proxy")
@EqualsAndHashCode(callSuper = true)
public class CoreProxy extends Model<CoreProxy> {
    /**
     * id
     */
    @TableId(value = "id")
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 当前用户的父id 也就是新增用户邀请人的id
     */
    @ApiModelProperty(value="新增用户邀请人的id")
    private Long pId;

    /**
     * 当前用户的id
     */
    @ApiModelProperty(value="当前用户的id")
    private Long userId;

    /**
     * 组织id 当前用户所属组织
     */
    @ApiModelProperty(value="组织id 当前用户所属组织")
    private Long organId;

    /**
     * 下级人数
     */
    @ApiModelProperty(value="下级人数")
    private Integer subordinateNum;

    /**
     * 层级类型 销售代/其他
     */
    @ApiModelProperty(value="层级类型 销售代/其他")
    private Integer type;

    /**
     * 当前层级 用户的当前层级
     */
    @ApiModelProperty(value="当前层级 用户的当前层级")
    private Integer level;

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

    public void toCoreProxyUpdateDTO(CoreProxyUpdateDTO coreProxyUpdateDTO){
        this.setPId(coreProxyUpdateDTO.getPId());
        this.setUserId(coreProxyUpdateDTO.getUserId());
        this.setOrganId(coreProxyUpdateDTO.getOrganId());
        this.setLevel(coreProxyUpdateDTO.getLevel());
        this.setType(coreProxyUpdateDTO.getType());

    }

    public void  toCoreProxyDto(CoreProxyDTO coreProxyDto){
        this.setPId(coreProxyDto.getPId());
        this.setOrganId(coreProxyDto.getOrganId());
        this.setLevel(coreProxyDto.getLevel());
        this.setType(coreProxyDto.getType());
        this.setUserId(coreProxyDto.getUserId());
        this.setSubordinateNum(coreProxyDto.getSubordinateNum());
    }
    public CoreProxyVO toCoreProxyVo(){
        CoreProxyVO coreProxyVo = new CoreProxyVO();
        coreProxyVo.setId(this.getId());
        coreProxyVo.setPId(this.getPId());
        coreProxyVo.setUserId(this.getUserId());
        coreProxyVo.setOrganId(this.getOrganId());
        coreProxyVo.setSubordinateNum(this.getSubordinateNum());
        coreProxyVo.setType(this.getType());
        coreProxyVo.setLevel(this.getLevel());
        return coreProxyVo;
    }

    public CoreProxyNodeVO toCoreProxyNodeVO(){
        CoreProxyNodeVO coreProxyNodeVO = new CoreProxyNodeVO();
        coreProxyNodeVO.setId(this.getId());
        coreProxyNodeVO.setPId(this.getPId());
        coreProxyNodeVO.setOrganId(this.getOrganId());
        coreProxyNodeVO.setUserId(this.getUserId());
        coreProxyNodeVO.setSubordinateNum(this.getSubordinateNum());
       coreProxyNodeVO.setType(this.getType());
       coreProxyNodeVO.setLevel(this.getLevel());
        return coreProxyNodeVO;
    }
}