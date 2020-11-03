package com.r7.core.profit.dto;

import com.r7.core.profit.constant.IncomeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author wutao
 * @Description 发放记录明细传输层
 */
@ApiModel(description = "发放记录明细传输层")
@Data
public class CoreRecordIncomeDTO  {

    /**
     * 平台ID
     */
    @NotNull(message = "平台ID不能为空")
    @ApiModelProperty(value="平台ID")
    private Long appId;



    /**
     * 分润用户id
     */
    @NotNull(message = "分润用户id不能为空")
    @ApiModelProperty(value="分润用户id")
    private Long userId;

    /**
     * 发放时间
     */

    @ApiModelProperty(value="发放时间")
    private LocalDateTime distributionAt;

    /**
     * 发放金额
     */
    @ApiModelProperty(value="发放金额")
    private Integer distributionAmount;

    /**
     * 发放积分
     */
    @ApiModelProperty(value="发放积分")
    private Integer distributionIntegral;

    /**
     * 计算分润条数
     */
    @NotNull(message = "计算分润条数不能为空")
    @ApiModelProperty(value="计算分润条数")
    private Integer countNum;

    /**
     * 发放状态 1未发放2已发放
     */
    @NotNull(message = "发放状态不能为空")
    @ApiModelProperty(value="发放状态 1未发放2已发放")
    private IncomeEnum status;

    /**
     * 描述
     */
    @NotBlank(message = "描述不能为空")
    @ApiModelProperty(value="描述")
    private String description;


}
