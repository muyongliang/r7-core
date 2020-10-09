package com.r7.core.uim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 组织修改
 *
 * @author zhongpingli
 */
@Data
@ApiModel("组织修改传输类")
public class UimOrganUpdateDTO {

    /**
     * 父组织
     */
    @ApiModelProperty("父id")
    @NotNull(message = "父组织不能为空")
    private Long pId;
    /**
     * 组织编码
     */
    @ApiModelProperty("组织编码")
    @NotEmpty(message = "组织编码不能为空")
    private String organCode;
    /**
     * 组织名称
     */
    @ApiModelProperty("组织名称")
    @NotEmpty(message = "组织名称不能为空")
    private String organName;
    /**
     * 类型;1组织0部门
     */
    @ApiModelProperty(value = "组织类型", example = "1")
    @NotEmpty(message = "组织类型不能为空")
    private Integer type;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    @NotEmpty(message = "组织排序不能为空")
    private Integer sort;

}
