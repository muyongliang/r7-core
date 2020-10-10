package com.r7.core.uim.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织展示
 *
 * @author zhongpingli
 */
@Data
@ApiModel("组织展示")
public class UimOrganVO {
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
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;
}
