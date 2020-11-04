package com.r7.core.assets.wallet.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.assets.wallet.constant.*;
import com.r7.core.assets.wallet.dto.CoreWalletExtractionSaveDTO;
import com.r7.core.assets.wallet.dto.CoreWalletExtractionUpdateDTO;
import com.r7.core.assets.wallet.vo.CoreWalletExtractionVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author zs
 * @description: 钱包提现明细表
 * @date : 2020-10-28
 */
@Data
@ApiModel("钱包提现明细表")
@TableName("core_wallet_extraction")
@EqualsAndHashCode(callSuper = true)
public class CoreWalletExtraction extends Model<CoreWalletExtraction> {
    /**
     * id
     */
    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 外部订单号
     */
    @ApiModelProperty("外部订单号")
    private String outOrderSn;
    /**
     * 提现账户json
     */
    @ApiModelProperty("提现账户json")
    private String extractionAccount;
    /**
     * 渠道标识;1支付宝2微信3银联4其他
     */
    @ApiModelProperty(value = "渠道标识", example = "1支付宝2微信3银联4其他")
    private WalletExtractionChannelCodeEnum channelCode;
    /**
     * 提现金额
     */
    @ApiModelProperty("提现金额")
    private Integer extractionAmount;
    /**
     * 实际发放金额
     */
    @ApiModelProperty("实际发放金额")
    private Integer distributionAmount;
    /**
     * 手续比例
     */
    @ApiModelProperty("手续比例")
    private Integer rate;
    /**
     * 提现状态;1未到账2已到账3提现失败
     */
    @ApiModelProperty(value = "提现状态", example = "1未到账2已到账3提现失败")
    private WalletExtractionExtractionStatusEnum extractionStatus;
    /**
     * 手续费
     */
    @ApiModelProperty("手续费")
    private Integer fee;
    /**
     * 审核状态;1待审核2审核中3审核通过4审核驳回
     */
    @ApiModelProperty(value = "审核状态", example = "1待审核2审核中3审核通过4审核驳回")
    private WalletExtractionStatusEnum status;
    /**
     * 提现进度状态;1提现审核中、2提现中、3提现失败、4提现成功
     */
    @ApiModelProperty(value = "提现进度状态", example = "1提现审核中、2提现中、3提现失败、4提现成功")
    private WalletExtractionScheduleEnum schedule;
    /**
     * 异常处理方式;1已退回2已重提3异常4正常
     */
    @ApiModelProperty(value = "异常处理方式", example = "1已退回2已重提3异常4正常")
    private WalletExtractionErrorDealWayEnum errorDealWay;
    /**
     * 审核失败原因
     */
    @ApiModelProperty("审核失败原因")
    private String reason;
    /**
     * 提现返回结果;给用户看，可为假的
     */
    @ApiModelProperty(value = "提现返回结果", example = "给用户看，可为假的")
    private String returnResult;
    /**
     * 日期;yyyyMMdd
     */
    @ApiModelProperty(value = "日期", example = "yyyyMMdd")
    private Integer extractionDate;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Long createdBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createdAt;
    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private Long updatedBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updatedAt;

    public void toCoreWalletExtraction(CoreWalletExtractionSaveDTO coreWalletExtractionSaveDto) {
        this.setUserId(coreWalletExtractionSaveDto.getUserId());
        this.setOutOrderSn(coreWalletExtractionSaveDto.getOutOrderSn());
        this.setExtractionAccount(coreWalletExtractionSaveDto.getExtractionAccount());
        this.setChannelCode(coreWalletExtractionSaveDto.getChannelCode());
        this.setExtractionAmount(coreWalletExtractionSaveDto.getExtractionAmount());
        this.setDistributionAmount(coreWalletExtractionSaveDto.getDistributionAmount());
        this.setRate(coreWalletExtractionSaveDto.getRate());
        this.setExtractionStatus(coreWalletExtractionSaveDto.getExtractionStatus());
        this.setFee(coreWalletExtractionSaveDto.getFee());
        this.setStatus(coreWalletExtractionSaveDto.getStatus());
        this.setSchedule(coreWalletExtractionSaveDto.getSchedule());
        this.setErrorDealWay(coreWalletExtractionSaveDto.getErrorDealWay());
        this.setReason(coreWalletExtractionSaveDto.getReason());
        this.setReturnResult(coreWalletExtractionSaveDto.getReturnResult());
        this.setDescription(coreWalletExtractionSaveDto.getDescription());
    }

    public void toCoreWalletExtraction(CoreWalletExtractionUpdateDTO coreWalletExtractionUpdateDto) {
        this.setUserId(coreWalletExtractionUpdateDto.getUserId());
        this.setDistributionAmount(coreWalletExtractionUpdateDto.getDistributionAmount());
        this.setRate(coreWalletExtractionUpdateDto.getRate());
        this.setExtractionStatus(coreWalletExtractionUpdateDto.getExtractionStatus());
        this.setFee(coreWalletExtractionUpdateDto.getFee());
        this.setStatus(coreWalletExtractionUpdateDto.getStatus());
        this.setSchedule(coreWalletExtractionUpdateDto.getSchedule());
        this.setErrorDealWay(coreWalletExtractionUpdateDto.getErrorDealWay());
        this.setReason(coreWalletExtractionUpdateDto.getReason());
        this.setReturnResult(coreWalletExtractionUpdateDto.getReturnResult());
        this.setDescription(coreWalletExtractionUpdateDto.getDescription());
    }

    public CoreWalletExtractionVO toCoreWalletExtractionVo() {
        CoreWalletExtractionVO coreWalletExtractionVo = new CoreWalletExtractionVO();
        coreWalletExtractionVo.setId(this.getId());
        coreWalletExtractionVo.setUserId(this.getUserId());
        coreWalletExtractionVo.setOutOrderSn(this.getOutOrderSn());
        coreWalletExtractionVo.setExtractionAccount(this.getExtractionAccount());
        coreWalletExtractionVo.setChannelCode(this.getChannelCode());
        coreWalletExtractionVo.setExtractionAmount(this.getExtractionAmount());
        coreWalletExtractionVo.setDistributionAmount(this.getDistributionAmount());
        coreWalletExtractionVo.setRate(this.getRate());
        coreWalletExtractionVo.setExtractionStatus(this.getExtractionStatus());
        coreWalletExtractionVo.setFee(this.getFee());
        coreWalletExtractionVo.setStatus(this.getStatus());
        coreWalletExtractionVo.setSchedule(this.getSchedule());
        coreWalletExtractionVo.setErrorDealWay(this.getErrorDealWay());
        coreWalletExtractionVo.setReason(this.getReason());
        coreWalletExtractionVo.setReturnResult(this.getReturnResult());
        coreWalletExtractionVo.setDescription(this.getDescription());
        coreWalletExtractionVo.setCreatedAt(this.getCreatedAt());
        coreWalletExtractionVo.setUpdatedAt(this.getUpdatedAt());
        return coreWalletExtractionVo;
    }
}