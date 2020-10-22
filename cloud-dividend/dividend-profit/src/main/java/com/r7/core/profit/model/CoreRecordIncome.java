package com.r7.core.profit.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.profit.dto.CoreRecordIncomeDTO;
import com.r7.core.profit.vo.CoreRecordIncomeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 
 * @Description 发放记录明细实体
 * @author wutao
 *
 */
@ApiModel(value="发放记录明细实体")
@Data
@TableName(value = "core_record_income")
@EqualsAndHashCode(callSuper = true)
public class CoreRecordIncome extends Model<CoreRecordIncome> {
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

    /**
     * 日期 yyyyMMdd
     */
    @ApiModelProperty(value="日期 yyyyMMdd")
    private Integer incomeDate;

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

    public void toCoreRecordIncomeDTO(CoreRecordIncomeDTO coreRecordIncomeDTO){
        this.setAppId(coreRecordIncomeDTO.getAppId());
        this.setUserId(coreRecordIncomeDTO.getUserId());
        this.setDistributionAt(coreRecordIncomeDTO.getDistributionAt());
        this.setDistributionAmount(coreRecordIncomeDTO.getDistributionAmount());
        this.setDistributionIntegral(coreRecordIncomeDTO.getDistributionIntegral());
        this.setCountNum(coreRecordIncomeDTO.getCountNum());
        this.setStatus(coreRecordIncomeDTO.getStatus());
        this.setDescription(coreRecordIncomeDTO.getDescription());
    }
    public CoreRecordIncomeVO toCoreRecordIncomeVO(){
        CoreRecordIncomeVO coreRecordIncomeVO = new CoreRecordIncomeVO();
        coreRecordIncomeVO.setId(this.getId());
        coreRecordIncomeVO.setAppId(this.getAppId());
        coreRecordIncomeVO.setUserId(this.getUserId());
        coreRecordIncomeVO.setDistributionAt(this.getDistributionAt());
        coreRecordIncomeVO.setDistributionAmount(this.getDistributionAmount());
        coreRecordIncomeVO.setDistributionIntegral(this.getDistributionIntegral());
        coreRecordIncomeVO.setCountNum(this.getCountNum());
        coreRecordIncomeVO.setStatus(this.getStatus());
        coreRecordIncomeVO.setDescription(this.getDescription());

        return coreRecordIncomeVO;
    }
}