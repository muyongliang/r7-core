package com.r7.core.uim.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 组织节点
 *
 * @author zhongpingli
 */
@Data
@ApiModel("组织节点")
public class UimOrganNodeVO {

    @ApiModelProperty("id")
    private Long id;
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

    @ApiModelProperty(value = "子组织节点")
    private List<UimOrganNodeVO> subNodes = Lists.newArrayList();

}
