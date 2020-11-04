package com.r7.core.assets.wallet.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.assets.wallet.constant.AccountChannelEnum;
import com.r7.core.assets.wallet.constant.AccountTypeEnum;
import com.r7.core.assets.wallet.dto.CoreAccountDTO;
import com.r7.core.assets.wallet.vo.CoreAccountVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author zs
 * @description: 核心账户表
 * @date : 2020-10-28
 */
@Data
@ApiModel("核心账户表")
@TableName("core_account")
@EqualsAndHashCode(callSuper = true)
public class CoreAccount extends Model<CoreAccount> {
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
     * 组织id
     */
    @ApiModelProperty("组织id")
    private Long organId;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;
    /**
     * 用户账户
     */
    @ApiModelProperty("用户账户")
    private String account;
    /**
     * 提现渠道;1支付宝；2微信；3银联；4股票；5其他
     */
    @ApiModelProperty(value = "提现渠道", example = "1支付宝；2微信；3银联；4股票；5其他")
    private AccountChannelEnum channel;
    /**
     * 类型;1提现账号；2转账账号；3其它
     */
    @ApiModelProperty(value = "类型", example = "1提现账号；2转账账号；3其它")
    private AccountTypeEnum type;
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

    public void toCoreAccount(CoreAccountDTO coreAccountDto) {
        this.setUserId(coreAccountDto.getUserId());
        this.setAccount(coreAccountDto.getAccount());
        this.setChannel(coreAccountDto.getChannel());
        this.setType(coreAccountDto.getType());
    }

    public CoreAccountVO toCoreAccountVo() {
        CoreAccountVO coreAccountVO = new CoreAccountVO();
        coreAccountVO.setId(this.getId());
        coreAccountVO.setAppId(this.getAppId());
        coreAccountVO.setOrganId(this.getOrganId());
        coreAccountVO.setUserId(this.getUserId());
        coreAccountVO.setAccount(this.getAccount());
        coreAccountVO.setChannel(this.getChannel());
        coreAccountVO.setType(this.getType());
        return coreAccountVO;
    }
}