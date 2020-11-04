package com.r7.core.assets.wallet.dto;

import com.r7.core.assets.wallet.constant.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zs
 * @description: 钱包提现明细新增传输层
 * @date : 2020-10-28
 */
@Data
@ApiModel("钱包提现明细新增传输层")
public class CoreWalletExtractionSaveDTO {
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;
    /**
     * 外部订单号
     */
    @ApiModelProperty("外部订单号")
    @NotBlank(message = "外部订单号不能为空")
    private String outOrderSn;
    /**
     * 提现账户json
     */
    @ApiModelProperty("提现账户json")
    @NotBlank(message = "提现账户不能为空")
    private String extractionAccount;
    /**
     * 渠道标识;1支付宝2微信3银联4其他
     */
    @ApiModelProperty(value = "渠道标识", example = "1支付宝2微信3银联4其他")
    @NotNull(message = "渠道标识不能为空")
    private WalletExtractionChannelCodeEnum channelCode;
    /**
     * 提现金额
     */
    @ApiModelProperty("提现金额")
    @NotNull(message = "提现金额不能为空")
    private Integer extractionAmount;
    /**
     * 实际发放金额
     */
    @ApiModelProperty("实际发放金额")
    @NotNull(message = "实际发放金额不能为空")
    private Integer distributionAmount;
    /**
     * 手续比例
     */
    @ApiModelProperty("手续比例")
    @NotNull(message = "手续比例不能为空")
    private Integer rate;
    /**
     * 提现状态;1未到账2已到账3提现失败
     */
    @ApiModelProperty(value = "提现状态", example = "1未到账2已到账3提现失败")
    @NotNull(message = "提现状态不能为空")
    private WalletExtractionExtractionStatusEnum extractionStatus;
    /**
     * 手续费
     */
    @ApiModelProperty("手续费")
    @NotNull(message = "手续费不能为空")
    private Integer fee;
    /**
     * 审核状态;1待审核2审核中3审核通过4审核驳回
     */
    @ApiModelProperty(value = "审核状态", example = "1待审核2审核中3审核通过4审核驳回")
    @NotNull(message = "审核状态不能为空")
    private WalletExtractionStatusEnum status;
    /**
     * 提现进度状态;1提现审核中、2提现中、3提现失败、4提现成功
     */
    @ApiModelProperty(value = "提现进度状态", example = "1提现审核中、2提现中、3提现失败、4提现成功")
    @NotNull(message = "提现进度状态不能为空")
    private WalletExtractionScheduleEnum schedule;
    /**
     * 异常处理方式;1已退回2已重提3异常4正常
     */
    @ApiModelProperty(value = "异常处理方式", example = "1已退回2已重提3异常4正常")
    @NotNull(message = "异常处理方式不能为空")
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
    @NotBlank(message = "提现返回结果并能为空")
    private String returnResult;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    @NotBlank(message = "描述不能为空")
    private String description;
}
