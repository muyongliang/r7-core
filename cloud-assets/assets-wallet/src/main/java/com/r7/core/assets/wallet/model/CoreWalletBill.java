package com.r7.core.assets.wallet.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.assets.wallet.constant.WalletBillIsShowEnum;
import com.r7.core.assets.wallet.constant.WalletBillStatusEnum;
import com.r7.core.assets.wallet.constant.WalletBillTypeEnum;
import com.r7.core.assets.wallet.dto.CoreWalletBillDTO;
import com.r7.core.assets.wallet.vo.CoreWalletBillVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author zs
 * @description: 钱包账单表
 * @date : 2020-10-27
 */
@Data
@ApiModel(description = "钱包账单表")
@TableName("core_wallet_bill")
@EqualsAndHashCode(callSuper = true)
public class CoreWalletBill extends Model<CoreWalletBill> {
    /**
     * id
     */
    @TableId
    @ApiModelProperty("id")
    private Long id;
    /**
     * 平台id
     */
    @ApiModelProperty("平台id")
    private Long appId;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 交易组织id
     */
    @ApiModelProperty("交易组织id")
    private Long organId;
    /**
     * 交易用户id
     */
    @ApiModelProperty("交易用户id")
    private Long transactionUserId;
    /**
     * 业务单号;购买订单号
     */
    @ApiModelProperty("业务单号;购买订单号")
    private String businessSn;
    /**
     * 是否展示;1不展示2展示
     */
    @ApiModelProperty(value = "是否展示", example = "1不展示2展示")
    private WalletBillIsShowEnum isShow;
    /**
     * 交易金额
     */
    @ApiModelProperty("交易金额 ")
    private Integer transactionAmount;
    /**
     * 交易类型;收入1/支出2/冻结3/解冻4
     */
    @ApiModelProperty(value = "交易类型", example = "收入1/支出2/冻结3/解冻4")
    private WalletBillTypeEnum type;
    /**
     * 交易来源;如购买商品，余额充值，余额提现
     */
    @ApiModelProperty(value = "交易来源", example = "如购买商品，余额充值，余额提现")
    private String source;
    /**
     * 钱包余额
     */
    @ApiModelProperty("钱包余额")
    private Integer balance;
    /**
     * 交易状态;1交易成功/2待支付/3支付失败
     */
    @ApiModelProperty(value = "交易状态", example = "1交易成功/2待支付/3支付失败")
    private WalletBillStatusEnum status;
    /**
     * 日期;yyyyMMdd
     */
    @ApiModelProperty(value = "日期", example = "yyyyMMdd")
    private Integer transactionDate;
    /**
     * 钱包余额交易描述;如：股票转账、充值、买文章、文章收费、积分购买、权益商品卖出等
     */
    @ApiModelProperty(value = "钱包余额交易描述", example = "如：股票转账、充值、买文章、文章收费、积分购买、权益商品卖出等")
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

    public void toCoreWalletBill(CoreWalletBillDTO coreWalletBillDto) {
        this.setUserId(coreWalletBillDto.getUserId());
        this.setTransactionUserId(coreWalletBillDto.getTransactionUserId());
        this.setBusinessSn(coreWalletBillDto.getBusinessSn());
        this.setIsShow(coreWalletBillDto.getIsShow());
        this.setTransactionAmount(coreWalletBillDto.getTransactionAmount());
        this.setType(coreWalletBillDto.getType());
        this.setSource(coreWalletBillDto.getSource());
        this.setStatus(coreWalletBillDto.getStatus());
        this.setDescription(coreWalletBillDto.getDescription());
    }

    public CoreWalletBillVO toCoreWalletBillVo() {
        CoreWalletBillVO coreWalletBillVo = new CoreWalletBillVO();
        coreWalletBillVo.setId(this.getId());
        coreWalletBillVo.setAppId(this.getAppId());
        coreWalletBillVo.setUserId(this.getUserId());
        coreWalletBillVo.setOrganId(this.getOrganId());
        coreWalletBillVo.setTransactionUserId(this.getTransactionUserId());
        coreWalletBillVo.setBusinessSn(this.getBusinessSn());
        coreWalletBillVo.setTransactionAmount(this.getTransactionAmount());
        coreWalletBillVo.setCoreWalletBillType(this.getType());
        coreWalletBillVo.setSource(this.getSource());
        coreWalletBillVo.setBalance(this.getBalance());
        coreWalletBillVo.setType(this.getStatus());
        coreWalletBillVo.setTransactionDate(this.getTransactionDate());
        coreWalletBillVo.setDescription(this.getDescription());
        coreWalletBillVo.setCreatedAt(this.getCreatedAt());
        return coreWalletBillVo;
    }
}