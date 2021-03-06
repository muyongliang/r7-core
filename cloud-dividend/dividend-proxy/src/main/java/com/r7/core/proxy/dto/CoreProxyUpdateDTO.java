package com.r7.core.proxy.dto;

import com.r7.core.proxy.constant.ProxyTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 *
 * @Description 代理层级信息修改传输层
 * @author wutao
 *
 */
@ApiModel(description = "代理层级信息传输层")
@Data
public class CoreProxyUpdateDTO  {


    /**
     * 组织id 当前用户所属组织
     */
    @NotNull(message = "当前用户组织id不能为空")
    @ApiModelProperty(value="组织id 当前用户所属组织")
    private Long organId;

    /**
     * 层级类型 销售代/其他
     */
    @NotNull(message = "层级类型不能为空")
    @ApiModelProperty(value="层级类型 销售代/其他")
    private ProxyTypeEnum type;

}