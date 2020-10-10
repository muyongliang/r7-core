package com.r7.core.proxy.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wutao
 * @Description 代理层级视图
 * @date 2020/10/9
 */
@Data
@ApiModel("代理层级视图")
public class CoreProxyVo {

    /**
     * id
     */
    @TableId(value = "id")
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 当前用户的父id 也就是新增用户邀请人的id
     */
    @ApiModelProperty(value="当前用户的父id 也就是新增用户邀请人的id")
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

}
