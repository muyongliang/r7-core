package com.r7.core.assets.wallet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.assets.wallet.constant.CoreWalletErrorEnum;
import com.r7.core.assets.wallet.dto.CoreWalletDTO;
import com.r7.core.assets.wallet.dto.CoreWalletUpdateDTO;
import com.r7.core.assets.wallet.mapper.CoreWalletMapper;
import com.r7.core.assets.wallet.model.CoreWallet;
import com.r7.core.assets.wallet.service.CoreWalletService;
import com.r7.core.assets.wallet.vo.CoreWalletVO;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zs
 * @description: 核心钱包服务实现层
 * @date : 2020-10-26
 */
@Slf4j
@Service
public class CoreWalletServiceImpl extends ServiceImpl<CoreWalletMapper, CoreWallet> implements CoreWalletService {


    @Override
    public Boolean saveWallet(CoreWalletDTO coreWalletDto, Long appId, Long organId, Long userId) {
        Long walletUserId = coreWalletDto.getUserId();
        log.info("平台:{}下组织:{}中的用户:{}创建钱包,操作者:{}", appId, organId, walletUserId, userId);
        Long id = SnowflakeUtil.getSnowflakeId();
        CoreWallet coreWallet = new CoreWallet();
        coreWallet.setId(id);
        coreWallet.toCoreWallet(coreWalletDto);
        coreWallet.setCreatedBy(userId);
        coreWallet.setCreatedAt(new Date());
        coreWallet.setUpdateBy(userId);
        coreWallet.setUpdateAt(new Date());
        boolean save = save(coreWallet);
        if (!save) {
            log.info("平台:{}下组织:{}中的用户:{}创建钱包失败,操作者:{}", appId, organId, walletUserId, userId);
            throw new BusinessException(CoreWalletErrorEnum.WALLET_SAVE_ERROR);
        }
        log.info("平台:{}下组织:{}中的用户:{}创建钱包成功,操作者:{}", appId, organId, walletUserId, userId);
        return true;
    }

    @Override
    public Boolean updateWalletByUserId(Long userId, CoreWalletUpdateDTO coreWalletUpdateDto, Long appId, Long organId) {
        log.info("平台:{}下组织:{}中的用户:{}修改钱包余额，操作人:{}", appId, organId, userId, userId);
        Option.of(userId).getOrElseThrow(() -> new BusinessException(CoreWalletErrorEnum.WALLET_USER_ID_IS_NOT_EXISTS));
        CoreWallet coreWallet = getOne(Wrappers.<CoreWallet>lambdaQuery().eq(CoreWallet::getUserId, userId));
        coreWallet.toCoreWalletUpdateDto(coreWalletUpdateDto);
        coreWallet.setUpdateBy(userId);
        coreWallet.setUpdateAt(new Date());
        boolean update = updateById(coreWallet);
        if (!update) {
            log.info("平台:{}下组织:{}中的用户:{}修改钱包余额失败，操作人:{}", appId, organId, userId, userId);
            throw new BusinessException(CoreWalletErrorEnum.WALLET_BALANCE_UPDATE_ERROR);
        }
        log.info("平台:{}下组织:{}中的用户:{}修改钱包余额成功，操作人:{}", appId, organId, userId, userId);
        return true;
    }

    @Override
    public CoreWalletVO getWalletByUserId(Long userId) {
        userId = Option.of(userId).getOrElseThrow(() -> new BusinessException(CoreWalletErrorEnum.USER_ID_IS_NULL));
        return Option.of(getOne(Wrappers.<CoreWallet>lambdaQuery()
                .eq(CoreWallet::getUserId, userId)))
                .map(CoreWallet::toCoreWalletVo).getOrNull();
    }

    @Override
    public IPage<CoreWalletVO> pageWallet(Integer pageNum, Integer pageSize) {
        Page<CoreWalletVO> page = new Page<>(pageNum, pageSize);
        return baseMapper.pageWallet(page);
    }

    @Override
    public Integer getTotalBalance() {

        QueryWrapper<CoreWallet> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(balance) as balance");
        CoreWallet wallet = getOne(queryWrapper);
        CoreWallet coreWallet = new CoreWallet();
        if (wallet == null) {
            coreWallet.setBalance(Integer.valueOf(0));
        } else {
            coreWallet.setBalance(wallet.getBalance());
        }
        return coreWallet.getBalance();
    }
}
