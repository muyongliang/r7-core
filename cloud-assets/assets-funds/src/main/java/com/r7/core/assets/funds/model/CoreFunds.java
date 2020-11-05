package com.r7.core.assets.funds.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.assets.funds.constant.FundsChannelEnum;
import com.r7.core.assets.funds.constant.FundsStatusEnum;
import com.r7.core.assets.funds.constant.FundsTransactionStatusEnum;
import com.r7.core.assets.funds.dto.CoreFundsDTO;
import com.r7.core.assets.funds.vo.CoreFundsVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author zs
 * @description: 核心资金表
 * @date : 2020-10-28
 */
@Data
@TableName("core_funds")
@ApiModel("核心资金表")
@EqualsAndHashCode(callSuper = true)
public class CoreFunds extends Model<CoreFunds> {
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
     * 内部订单号
     */
    @ApiModelProperty("内部订单号")
    private Long inOrderSn;
    /**
     * 外部订单号
     */
    @ApiModelProperty("外部订单号")
    private String outOrderSn;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organId;
    /**
     * 支付链接
     */
    @ApiModelProperty("支付链接")
    private String payLink;
    /**
     * 交易金额
     */
    @ApiModelProperty("交易金额")
    private Integer amount;
    /**
     * 支付时间
     */
    @ApiModelProperty("支付时间")
    private Date payDate;
    /**
     * 支付状态;，1交易成功，2交易失败,3待支付，4取消支付
     */
    @ApiModelProperty(value = "支付状态", example = "1交易成功，2交易失败,3待支付，4取消支付")
    private FundsStatusEnum status;
    /**
     * 交易状态;1待支付2已支付3支付失败
     */
    @ApiModelProperty(value = "交易状态", example = "1待支付2已支付3支付失败")
    private FundsTransactionStatusEnum transactionStatus;
    /**
     * 支付渠道;1支付宝2微信3其他
     */
    @ApiModelProperty(value = "支付渠道", example = "1支付宝2微信3其他")
    private FundsChannelEnum channel;
    /**
     * 收益日期;yyyyMMdd
     */
    @ApiModelProperty(value = "收益日期", example = "yyyyMMdd")
    private Integer incomeDate;
    /**
     * 交易订单详情描述
     */
    @ApiModelProperty("交易订单详情描述")
    private String description;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Long createdBy;
    /**
     * 创建时间;充值时间
     */
    @ApiModelProperty("创建时间,充值时间")
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

    public void toCoreFunds(CoreFundsDTO coreFundsDto) {
        this.setInOrderSn(coreFundsDto.getInOrderSn());
        this.setOutOrderSn(coreFundsDto.getOutOrderSn());
        this.setUserId(coreFundsDto.getUserId());
        this.setPayLink(coreFundsDto.getPayLink());
        this.setAmount(coreFundsDto.getAmount());
        this.setPayDate(coreFundsDto.getPayDate());
        this.setStatus(coreFundsDto.getStatus());
        this.setTransactionStatus(coreFundsDto.getTransactionStatus());
        this.setChannel(coreFundsDto.getChannel());
        this.setDescription(coreFundsDto.getDescription());
    }

    public CoreFundsVO toCoreFundsVo() {
        CoreFundsVO coreFundsVo = new CoreFundsVO();
        coreFundsVo.setId(this.getId());
        coreFundsVo.setAppId(this.getAppId());
        coreFundsVo.setInOrderSn(this.getInOrderSn());
        coreFundsVo.setOutOrderSn(this.getUserId());
        coreFundsVo.setOrganId(this.getOrganId());
        coreFundsVo.setPayLink(this.getPayLink());
        coreFundsVo.setAmount(this.getAmount());
        coreFundsVo.setPayDate(this.getPayDate());
        coreFundsVo.setStatus(this.getStatus());
        coreFundsVo.setTransactionStatus(this.getTransactionStatus());
        coreFundsVo.setChannel(this.getChannel());
        coreFundsVo.setDescription(this.getDescription());
        return coreFundsVo;
    }
}