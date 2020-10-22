package com.r7.core.profit.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wutao
 * @Description 发放明细视图
 */
@ApiModel(description = "发放明细视图")
@Data
public class CoreRecordIncomeVO {

    /**
     * id
     */
    @TableId(value = "id")
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 平台ID
     */
    @ApiModelProperty(value="平台ID")
    private Long appId;

    /**
     * 分润明细表id
     */
    @ApiModelProperty(value="分润明细表id")
    private Long profitsId;

    /**
     * 分润用户id
     */
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
    @ApiModelProperty(value="计算分润条数")
    private Integer countNum;

    /**
     * 发放状态 1未发放2已发放
     */
    @ApiModelProperty(value="发放状态 1未发放2已发放")
    private Integer status;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String description;




}
