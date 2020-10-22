package com.r7.core.proxy.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *
 * @Description 代理层级信息传输层
 * @author wutao
 *
 */
@ApiModel(description = "代理层级信息传输层")
@Data
public class CoreProxyDTO  {


    /**
     * 当前用户的父id 也就是新增用户邀请人的id
     */
    @NotNull(message = "当前用户父ID不能为空")
    @ApiModelProperty(value="当前用户的父id 也就是新增用户邀请人的id")
    private Long pId;

    /**
     * 当前用户的id
     */
    @NotNull(message = "当前用户的id不能为空")
    @ApiModelProperty(value="当前用户的id")
    private Long userId;

    /**
     * 组织id 当前用户所属组织
     */
    @NotNull(message = "当前用户组织id不能为空")
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
    @NotNull(message = "层级类型不能为空")
    @ApiModelProperty(value="层级类型 销售代/其他")
    private Integer type;

    /**
     * 当前层级 用户的当前层级
     */
    @NotNull(message = "当前用户的层级不能为空")
    @ApiModelProperty(value="当前层级 用户的当前层级")
    private Integer level;


}