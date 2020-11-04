package com.r7.core.assets.wallet.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.assets.wallet.dto.CoreWalletDTO;
import com.r7.core.assets.wallet.dto.CoreWalletUpdateDTO;
import com.r7.core.assets.wallet.vo.CoreWalletVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author zs
 * @description: 钱包表
 * @date : 2020-10-27
 */
@Data
@ApiModel(description = "钱包表")
@TableName("core_wallet")
@EqualsAndHashCode(callSuper = true)
public class CoreWallet extends Model<CoreWallet> {
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
     * 钱包支付密码;6位数的支付密码
     */
    @ApiModelProperty(value = "钱包支付密码", example = "123456")
    private String payPassword;
    /**
     * 钱包总余额
     */
    @ApiModelProperty("钱包总余额")
    private Integer balance;
    /**
     * 不可用余额;不能提现的余额，权益仓库保管费类似押金
     */
    @ApiModelProperty("不可用余额")
    private Integer lockingBalance;
    /**
     * 签名;用户ID，钱包支付密码，钱包余额三者进行加密，增减时从数据库中
     */
    @ApiModelProperty("签名")
    private String sign;
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
    private Long updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateAt;

    public CoreWalletVO toCoreWalletVo() {
        CoreWalletVO coreWalletVo = new CoreWalletVO();
        coreWalletVo.setId(this.getId());
        coreWalletVo.setUserId(this.getUserId());
        coreWalletVo.setBalance(this.getBalance());
        coreWalletVo.setLockingBalance(this.getLockingBalance());
        return coreWalletVo;
    }

    public void toCoreWallet(CoreWalletDTO coreWalletDto) {
        this.setUserId(coreWalletDto.getUserId());
        this.setBalance(coreWalletDto.getBalance());
        this.setLockingBalance(coreWalletDto.getLockingBalance());
    }

    public void toCoreWalletUpdateDto(CoreWalletUpdateDTO coreWalletUpdateDto) {
        this.setPayPassword(coreWalletUpdateDto.getPayPassword());
        this.setBalance(coreWalletUpdateDto.getBalance());
        this.setLockingBalance(coreWalletUpdateDto.getLockingBalance());
        this.setSign(coreWalletUpdateDto.getSign());
    }
}