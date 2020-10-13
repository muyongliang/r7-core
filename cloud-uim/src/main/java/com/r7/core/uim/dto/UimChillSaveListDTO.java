package com.r7.core.uim.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zs
 * @description:
 * @date : 2020-10-13
 */
@Data
@ApiModel("批量冻结用户权限传输")
public class UimChillSaveListDTO {
    /**
     * 冻结用户
     */
    @ApiModelProperty("冻结用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;
    /**
     * 冻结资源;登录/邀请码/提现/平台冻结
     */
    @ApiModelProperty("冻结资源id")
    @NotNull(message = "资源id不能为空")
    private List<Long> resourceIds;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @NotEmpty(message = "描述不能为空")
    private String description;
}
