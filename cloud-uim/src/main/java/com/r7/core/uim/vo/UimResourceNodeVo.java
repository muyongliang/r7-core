package com.r7.core.uim.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源树节点
 *
 * @author zhongpingli
 */
@Data
@ApiModel("资源树节点")
public class UimResourceNodeVo {

    @ApiModelProperty("id")
    private Long id;
    /**
     * 资源父类
     */
    @ApiModelProperty("资源父类")
    private Long pId;

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


    @ApiModelProperty("子资源节点")
    private List<UimResourceNodeVo> subNodes = new ArrayList<>();


    public void addSubNode(UimResourceNodeVo subNode) { subNodes.add(subNode); }

}
