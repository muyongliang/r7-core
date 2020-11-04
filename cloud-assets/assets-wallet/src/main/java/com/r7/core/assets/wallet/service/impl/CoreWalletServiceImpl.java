package com.r7.core.assets.wallet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.assets.wallet.constant.RsaEnum;
import com.r7.core.assets.wallet.constant.WalletErrorEnum;
import com.r7.core.assets.wallet.dto.CoreWalletBalanceChangeDTO;
import com.r7.core.assets.wallet.dto.CoreWalletDTO;
import com.r7.core.assets.wallet.dto.CoreWalletLockingBalanceChangeDTO;
import com.r7.core.assets.wallet.mapper.CoreWalletMapper;
import com.r7.core.assets.wallet.model.CoreWallet;
import com.r7.core.assets.wallet.service.CoreWalletService;
import com.r7.core.assets.wallet.util.RsaUtil;
import com.r7.core.assets.wallet.vo.CoreWalletVO;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zs
 * @description: 核心钱包服务实现层
 * @date : 2020-10-26
 */
@Slf4j
@Service
public class CoreWalletServiceImpl extends ServiceImpl<CoreWalletMapper, CoreWallet> implements CoreWalletService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveWallet(CoreWalletDTO coreWalletDto, Long userId) {
        Long walletUserId = coreWalletDto.getUserId();
        log.info("钱包新增传输实体:{},操作时间:{}创建钱包,操作者:{}", walletUserId, LocalDateTime.now(), userId);
        Long id = SnowflakeUtil.getSnowflakeId();
        CoreWallet coreWallet = new CoreWallet();
        String payPassword = passwordEncoder.encode(coreWalletDto.getPayPassword());
        coreWallet.setId(id);
        String sign = RsaUtil.encryptByPublicKey("" + walletUserId + payPassword +
                coreWalletDto.getBalance() + coreWalletDto.getLockingBalance(), RsaEnum.PUBLIC_KEY);
        coreWallet.setSign(sign);
        coreWallet.toCoreWallet(coreWalletDto);
        coreWallet.setPayPassword(payPassword);
        coreWallet.setCreatedBy(userId);
        coreWallet.setCreatedAt(new Date());
        coreWallet.setUpdateBy(userId);
        coreWallet.setUpdateAt(new Date());
        boolean save = save(coreWallet);
        if (!save) {
            log.info("钱包新增传输实体:{}失败失败,操作时间:{}创建钱包,操作者:{}", walletUserId, LocalDateTime.now(), userId);
            throw new BusinessException(WalletErrorEnum.WALLET_SAVE_ERROR);
        }
        log.info("钱包新增传输实体:{}新增成功,操作时间:{}创建钱包,操作者:{}", walletUserId, LocalDateTime.now(), userId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateWalletPayPasswordById(Long updateUserId, String changePayPassword, Long userId) {
        log.info("用户id:{},操作时间:{},操作者:{}", updateUserId, LocalDateTime.now(), userId);
        Option.of(updateUserId).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_ID_IS_NULL));
        CoreWallet coreWallet = Option.of(getWalletChangeByUserId(updateUserId))
                .getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_USER_ID_IS_NOT_EXISTS));
        Integer balance = coreWallet.getBalance();
        Integer lockingBalance = coreWallet.getLockingBalance();
        String oldPayPassword = coreWallet.getPayPassword();
        String payPassword = passwordEncoder.encode(changePayPassword);
        boolean result = RsaUtil.verify("" + updateUserId + oldPayPassword + balance + lockingBalance, coreWallet.getSign(), RsaEnum.PRIVATE_KEY);
        if (!result) {
            log.info("用户id:{},操作时间:{},操作者:{}", updateUserId, LocalDateTime.now(), userId);
            throw new BusinessException(WalletErrorEnum.WALLET_SIGN_ERROR);
        }
        String sign = RsaUtil.encryptByPublicKey("" + updateUserId + "" + payPassword + "" + balance + "" + lockingBalance, RsaEnum.PUBLIC_KEY);
        coreWallet.setPayPassword(payPassword);
        coreWallet.setSign(sign);
        coreWallet.setUpdateBy(userId);
        coreWallet.setUpdateAt(new Date());
        boolean update = updateById(coreWallet);
        if (!update) {
            log.info("用户id:{},修改密码失败,操作时间:{},操作者:{}", updateUserId, LocalDateTime.now(), userId);
            throw new BusinessException(WalletErrorEnum.WALLET_PAY_PASSWORD_UPDATE_ERROR);
        }
        log.info("用户id:{},修改密码成功,操作时间:{},操作者:{}", updateUserId, LocalDateTime.now(), userId);
        return true;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoreWalletVO updateCoreWalletAddBalance(
            Long updateUserId, CoreWalletBalanceChangeDTO coreWalletBalanceChangeDto, Long userId) {
        log.info("用户id:{},新增用户钱包余额信息:{},操作者:{}", updateUserId, coreWalletBalanceChangeDto, userId);
        Option.of(updateUserId).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_USER_ID_IS_NULL));
        Option.of(updateUserId).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_USER_ID_IS_NOT_EXISTS));
        CoreWallet coreWallet = getWalletChangeByUserId(updateUserId);
        String encoderPayPassword = coreWallet.getPayPassword();
        String payPassword = coreWalletBalanceChangeDto.getPayPassword();
        boolean matches = passwordEncoder.matches(payPassword, encoderPayPassword);
        if (!matches) {
            log.info("新增用户id:{}, 密码验证结果:{},操作者:{},操作时间:{}", updateUserId, "密码验证错误", userId, LocalDateTime.now());
            throw new BusinessException(WalletErrorEnum.WALLET_PAY_PASSWORD_ERROR);
        }
        Integer oldBalance = coreWallet.getBalance();
        Integer lockingBalance = coreWallet.getLockingBalance();
        boolean result = RsaUtil.verify("" + updateUserId + encoderPayPassword + oldBalance + lockingBalance,
                coreWallet.getSign(), RsaEnum.PRIVATE_KEY);
        if (!result) {
            log.info("新增用户id:{}, 签名验证结果:{},操作者:{},操作时间:{}", updateUserId, "签名验证失败", userId, LocalDateTime.now());
            throw new BusinessException(WalletErrorEnum.WALLET_SIGN_ERROR);
        }
        Integer balance = oldBalance + coreWalletBalanceChangeDto.getBalance();
        coreWallet.setBalance(balance);
        //生成新签名
        String sign = RsaUtil.encryptByPublicKey("" + updateUserId +
                encoderPayPassword + balance + lockingBalance, RsaEnum.PUBLIC_KEY);
        coreWallet.setSign(sign);
        coreWallet.setUpdateBy(userId);
        coreWallet.setUpdateAt(new Date());
        boolean update = updateById(coreWallet);
        if (!update) {
            log.info("用户id:{}, 新增用户钱包余额:{}失败,操作者:{}, 操作时间:{}", updateUserId, coreWalletBalanceChangeDto, userId, LocalDateTime.now());
            throw new BusinessException(WalletErrorEnum.WALLET_BALANCE_UPDATE_IS_ERROR);
        }
        log.info("用户id:{}, 用户钱包余额:{}成功,操作者:{}", updateUserId, coreWalletBalanceChangeDto, userId);
        return coreWallet.toCoreWalletVo();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoreWalletVO updateCoreWalletAddLockingBalance(
            Long updateUserId, CoreWalletLockingBalanceChangeDTO coreWalletLockingBalanceChangeDto, Long userId) {
        log.info("新增用户钱包不可用余额实体:{},操作时间:{},操作者:{}", coreWalletLockingBalanceChangeDto, LocalDateTime.now(), userId);
        Option.of(updateUserId).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_USER_ID_IS_NULL));
        CoreWallet coreWallet = Option.of(getWalletChangeByUserId(updateUserId))
                .getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_USER_ID_IS_NOT_EXISTS));
        String encoderPayPassword = coreWallet.getPayPassword();
        String payPassword = coreWalletLockingBalanceChangeDto.getPayPassword();
        Integer balance = coreWallet.getBalance();
        Integer oldLockingBalance = coreWallet.getLockingBalance();
        boolean matches = passwordEncoder.matches(payPassword, encoderPayPassword);
        if (!matches) {
            log.info("新增用户id:{}, 密码验证结果:{},操作者:{},操作时间:{}", updateUserId, "密码验证错误", userId, LocalDateTime.now());
            throw new BusinessException(WalletErrorEnum.WALLET_PAY_PASSWORD_ERROR);
        }
        boolean result = RsaUtil.verify("" + updateUserId + encoderPayPassword + balance + oldLockingBalance, coreWallet.getSign(), RsaEnum.PRIVATE_KEY);
        if (!result) {
            throw new BusinessException(WalletErrorEnum.WALLET_SIGN_ERROR);
        }
        int availableBalance = balance - oldLockingBalance;
        Integer changeLockingBalance = coreWalletLockingBalanceChangeDto.getLockingBalance();
        //判断增加的不可用余额是否大于总余额和不可用余额差值
        if (availableBalance < changeLockingBalance) {
            log.info("用户id:{}, 新增用户钱包不可用余额实体:{},验证结果:{},操作时间:{},操作者:{}", updateUserId, coreWalletLockingBalanceChangeDto, "验证失败", LocalDateTime.now(), userId);
            throw new BusinessException(WalletErrorEnum.WALLET_BALANCE_IS_NOT_ENOUGH);
        }
        Integer lockingBalance = changeLockingBalance + oldLockingBalance;
        //重新生成签名
        String sign = RsaUtil.encryptByPublicKey("" + updateUserId + encoderPayPassword + balance + lockingBalance, RsaEnum.PUBLIC_KEY);
        coreWallet.setLockingBalance(lockingBalance);
        coreWallet.setSign(sign);
        coreWallet.setUpdateBy(userId);
        coreWallet.setUpdateAt(new Date());
        boolean update = updateById(coreWallet);
        if (!update) {
            log.info("用户id:{}, 新增用户钱包不可用余额实体:{}修改失败,操作时间:{},操作者:{}", updateUserId, coreWalletLockingBalanceChangeDto, LocalDateTime.now(), userId);
            throw new BusinessException(WalletErrorEnum.WALLET_LOCKING_BALANCE_UPDATE_IS_ERROR);
        }
        log.info("用户id:{}, 新增用户钱包不可用余额实体:{}修改成功,操作时间:{},操作者:{}", updateUserId, coreWalletLockingBalanceChangeDto, LocalDateTime.now(), userId);
        return coreWallet.toCoreWalletVo();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoreWalletVO updateCoreWalletReduceBalance(
            Long updateUserId, CoreWalletBalanceChangeDTO coreWalletBalanceChangeDto, Long userId) {
        log.info("用户id:{}, 减少用户钱包总余额实体:{},操作时间:{},操作者:{}", updateUserId, coreWalletBalanceChangeDto, LocalDateTime.now(), userId);
        Option.of(updateUserId).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_USER_ID_IS_NULL));
        Option.of(updateUserId).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_USER_ID_IS_NOT_EXISTS));
        CoreWallet coreWallet = getWalletChangeByUserId(updateUserId);
        Integer oldBalance = coreWallet.getBalance();
        Integer lockingBalance = coreWallet.getLockingBalance();
        String encoderPayPassword = coreWallet.getPayPassword();
        String payPassword = coreWalletBalanceChangeDto.getPayPassword();
        boolean matches = passwordEncoder.matches(payPassword, encoderPayPassword);
        if (!matches) {
            log.info("新增用户id:{}, 密码验证结果:{},操作者:{},操作时间:{}", updateUserId, "密码验证错误", userId, LocalDateTime.now());
            throw new BusinessException(WalletErrorEnum.WALLET_PAY_PASSWORD_ERROR);
        }
        boolean result = RsaUtil.verify("" + updateUserId + encoderPayPassword + oldBalance + lockingBalance, coreWallet.getSign(), RsaEnum.PRIVATE_KEY);
        if (!result) {
            log.info("用户id:{},签名验证结果:{},操作时间:{},操作者:{}", updateUserId, "签名验证失败", LocalDateTime.now(), userId);
            throw new BusinessException(WalletErrorEnum.WALLET_SIGN_ERROR);
        }
        Integer reduceBalance = coreWalletBalanceChangeDto.getBalance();
        if ((oldBalance - lockingBalance) < reduceBalance) {
            throw new BusinessException(WalletErrorEnum.WALLET_BALANCE_IS_NOT_ENOUGH);
        }
        Integer balance = oldBalance - reduceBalance;
        String sign = RsaUtil.encryptByPublicKey("" + updateUserId + encoderPayPassword + balance + lockingBalance, RsaEnum.PUBLIC_KEY);
        coreWallet.setBalance(balance);
        coreWallet.setSign(sign);
        coreWallet.setUpdateBy(userId);
        coreWallet.setUpdateAt(new Date());
        boolean update = updateById(coreWallet);
        if (!update) {
            log.info("用户id:{}, 减少用户钱包总余额实体:{}操作失败,操作时间:{},操作者:{}", updateUserId, coreWalletBalanceChangeDto, LocalDateTime.now(), userId);
            throw new BusinessException(WalletErrorEnum.WALLET_BALANCE_UPDATE_ERROR);
        }
        log.info("用户id:{}, 减少用户钱包总余额实体:{}操作成功,操作时间:{},操作者:{}", updateUserId, coreWalletBalanceChangeDto, LocalDateTime.now(), userId);
        return coreWallet.toCoreWalletVo();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoreWalletVO updateCoreWalletReduceLockingBalance(
            Long updateUserId, CoreWalletLockingBalanceChangeDTO coreWalletLockingBalanceChangeDto, Long userId) {
        log.info("用户id:{}, 钱包不可用余额修改实体:{},操作修改,操作时间:{},操作者:{}", updateUserId, coreWalletLockingBalanceChangeDto, LocalDateTime.now(), userId);
        Option.of(updateUserId).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_USER_ID_IS_NULL));
        Option.of(updateUserId).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_USER_ID_IS_NOT_EXISTS));
        CoreWallet coreWallet = getWalletChangeByUserId(updateUserId);
        Integer balance = coreWallet.getBalance();
        Integer oldLockingBalance = coreWallet.getLockingBalance();
        String encoderPayPassword = coreWallet.getPayPassword();
        String payPassword = coreWalletLockingBalanceChangeDto.getPayPassword();
        boolean matches = passwordEncoder.matches(payPassword, encoderPayPassword);
        if (!matches) {
            log.info("新增用户id:{}, 密码验证结果:{},操作者:{},操作时间:{}", updateUserId, "密码验证错误", userId, LocalDateTime.now());
            throw new BusinessException(WalletErrorEnum.WALLET_PAY_PASSWORD_ERROR);
        }
        boolean result = RsaUtil.verify("" + updateUserId + encoderPayPassword + balance + oldLockingBalance, coreWallet.getSign(), RsaEnum.PRIVATE_KEY);
        if (!result) {
            log.info("用户id:{},签名验证结果:{},操作时间:{},操作者:{}", coreWalletLockingBalanceChangeDto, "签名验证失败", LocalDateTime.now(), userId);
            throw new BusinessException(WalletErrorEnum.WALLET_SIGN_ERROR);
        }
        Integer changeLockingBalance = coreWalletLockingBalanceChangeDto.getLockingBalance();
        if (oldLockingBalance < changeLockingBalance) {
            throw new BusinessException(WalletErrorEnum.WALLET_LOCKING_BALANCE_IS_NOT_ENOUGH);
        }
        Integer lockingBalance = oldLockingBalance - changeLockingBalance;
        String sign = RsaUtil.encryptByPublicKey("" + updateUserId + encoderPayPassword + balance + lockingBalance, RsaEnum.PUBLIC_KEY);
        coreWallet.setLockingBalance(lockingBalance);
        coreWallet.setSign(sign);
        coreWallet.setUpdateBy(userId);
        coreWallet.setUpdateAt(new Date());
        boolean update = updateById(coreWallet);
        if (!update) {
            log.info("用户id:{}, 钱包不可用余额修改实体:{},操作失败,修改时间:{},操作者:{}", updateUserId, coreWalletLockingBalanceChangeDto, LocalDateTime.now(), userId);
            throw new BusinessException(WalletErrorEnum.WALLET_LOCKING_BALANCE_UPDATE_IS_ERROR);
        }
        log.info("用户id:{}, 钱包不可用余额修改实体:{},操作成功,修改时间:{},操作者:{}", updateUserId, coreWalletLockingBalanceChangeDto, LocalDateTime.now(), userId);
        return coreWallet.toCoreWalletVo();
    }


    @Override
    public CoreWalletVO getWalletByUserId(Long userId) {
        userId = Option.of(userId).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.USER_ID_IS_NULL));
        return Option.of(getOne(Wrappers.<CoreWallet>lambdaQuery()
                .select(CoreWallet::getId, CoreWallet::getUserId,
                        CoreWallet::getBalance, CoreWallet::getLockingBalance)
                .eq(CoreWallet::getUserId, userId)))
                .map(CoreWallet::toCoreWalletVo).getOrNull();
    }

    @Override
    public Integer getWalletBalanceByUserId(Long userId) {
        Option.of(userId).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.USER_ID_IS_NULL));
        return Option.of(getOne(Wrappers.<CoreWallet>lambdaQuery()
                .select(CoreWallet::getBalance)
                .eq(CoreWallet::getUserId, userId)))
                .map(CoreWallet::getBalance).getOrNull();
    }

    @Override
    public CoreWallet getWalletChangeByUserId(Long userId) {
        Option.of(userId).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_USER_ID_IS_NULL));
        CoreWallet coreWallet = Option.of(getOne(Wrappers.<CoreWallet>lambdaQuery().eq(CoreWallet::getUserId, userId)))
                .getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_IS_NOT_EXISTS));
        return coreWallet;
    }

    @Override
    public CoreWalletVO getWalletById(Long id) {
        CoreWallet coreWallet = Option.of(getOne(Wrappers.<CoreWallet>lambdaQuery()
                .select(CoreWallet::getId, CoreWallet::getUserId,
                        CoreWallet::getBalance, CoreWallet::getLockingBalance)
                .eq(CoreWallet::getId, id))).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_ID_IS_NOT_EXISTS));
        return coreWallet.toCoreWalletVo();
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
