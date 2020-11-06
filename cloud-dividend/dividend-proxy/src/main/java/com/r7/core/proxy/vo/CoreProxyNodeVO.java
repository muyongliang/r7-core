package com.r7.core.proxy.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.r7.core.proxy.constant.ProxyTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wutao
 * @Description 层级树节点
 *
 */
@Data
@ApiModel("代理层级树形展示视图")
public class CoreProxyNodeVO {

    /**
     * id
     */
    @TableId(value = "层级id")
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
    private ProxyTypeEnum type;

    /**
     * 当前层级 用户的当前层级
     */
    @ApiModelProperty(value="当前层级 用户的当前层级")
    private Integer level;

    /**
     * 下级节点
     */
    @ApiModelProperty(value="下级节点")
    private List<CoreProxyNodeVO> list = new ArrayList<>();
}
