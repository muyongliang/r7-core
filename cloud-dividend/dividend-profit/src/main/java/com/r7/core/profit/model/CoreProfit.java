package com.r7.core.profit.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.profit.constant.CalculationStatusEnum;
import com.r7.core.profit.constant.ProfitTypeEnum;
import com.r7.core.profit.dto.CoreProfitDTO;
import com.r7.core.profit.vo.CoreProfitVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 
 * @Description 分润明细实体
 * @author wutao
 *
 */
@ApiModel(value="分润明细实体")
@Data
@TableName(value = "core_profit")
@EqualsAndHashCode(callSuper = true)
public class CoreProfit extends Model<CoreProfit> {
    /**
     * id
     */
    @TableId(value = "id")
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 平台id
     */
    @ApiModelProperty(value="平台id")
    private Long appId;

    /**
     * 分润方案id
     */
    @ApiModelProperty(value="分润方案id")
    private Long planId;

    /**
     * 分润用户id
     */
    @ApiModelProperty(value="分润用户id")
    private Long userId;

    /**
     * 发放记录id
     */
    @ApiModelProperty(value="发放记录id")
    private Long recordIncomeId;

    /**
     * 订单ID
     */
    @ApiModelProperty(value="订单ID")
    private Long orderId;

    /**
     * 层级
     */
    @ApiModelProperty(value="层级")
    private Integer level;

    /**
     * 分润金额
     */
    @ApiModelProperty(value="分润金额")
    private Integer amount;

    /**
     * 分润积分
     */
    @ApiModelProperty(value="分润积分")
    private Integer integral;

    /**
     * 分润的区分
     */
    @ApiModelProperty(value="1金额2积分")
    private Integer difference;

    /**
     * 计算状态 1未计算2已计算
     */
    @ApiModelProperty(value="计算状态 1未计算2已计算")
    private CalculationStatusEnum status;

    /**
     * 分润比例
     */
    @ApiModelProperty(value="分润比例")
    private Integer rate;

    /**
     * 分润类型 1权益商品2游戏分润
     */
    @ApiModelProperty(value="分润类型 1权益商品2游戏分润")
    private ProfitTypeEnum type;

    /**
     * 日期 yyyyMMdd
     */
    @ApiModelProperty(value="日期 yyyyMMdd")
    private Integer profitDate;

    /**
     * 小时
     */
    @ApiModelProperty(value="小时")
    private Integer hour;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String description;

    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createdAt;

    /**
     * 更新人
     */
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private LocalDateTime updatedAt;

    public void toCoreProfitDTO(CoreProfitDTO coreProfitDTO){
        this.setDifference(coreProfitDTO.getDifference());
        this.setAppId(coreProfitDTO.getAppId());
        this.setPlanId(coreProfitDTO.getPlanId());
        this.setUserId(coreProfitDTO.getUserId());
        this.setRecordIncomeId(coreProfitDTO.getRecordIncomeId());
        this.setOrderId(coreProfitDTO.getOrderId());
        this.setLevel(coreProfitDTO.getLevel());
        this.setAmount(coreProfitDTO.getAmount());
        this.setIntegral(coreProfitDTO.getIntegral());
        this.setStatus(coreProfitDTO.getStatus());
        this.setRate(coreProfitDTO.getRate());
        this.setType(coreProfitDTO.getType());
        this.setDescription(coreProfitDTO.getDescription());
    }

    public CoreProfitVO toCoreProfitVO(){
        CoreProfitVO coreProfitVO = new CoreProfitVO();
        coreProfitVO.setId(this.getId());
        coreProfitVO.setAppId(this.getAppId());
        coreProfitVO.setPlanId(this.getPlanId());
        coreProfitVO.setUserId(this.getUserId());
        coreProfitVO.setRecordIncomeId(this.getRecordIncomeId());
        coreProfitVO.setOrderId(this.getOrderId());
        coreProfitVO.setLevel(this.getLevel());
        coreProfitVO.setAmount(this.getAmount());
        coreProfitVO.setIntegral(this.getIntegral());
        coreProfitVO.setStatus(this.getStatus());
        coreProfitVO.setRate(this.getRate());
        coreProfitVO.setType(this.getType());
        coreProfitVO.setProfitDate(this.getProfitDate());
        coreProfitVO.setHour(this.getHour());
        coreProfitVO.setDescription(this.getDescription());
        coreProfitVO.setCreatedAt(this.getCreatedAt());
        return coreProfitVO;
    }

}