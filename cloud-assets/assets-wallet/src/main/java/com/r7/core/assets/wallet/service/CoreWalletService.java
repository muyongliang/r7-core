package com.r7.core.assets.wallet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.assets.wallet.dto.CoreWalletBalanceChangeDTO;
import com.r7.core.assets.wallet.dto.CoreWalletDTO;
import com.r7.core.assets.wallet.dto.CoreWalletLockingBalanceChangeDTO;
import com.r7.core.assets.wallet.model.CoreWallet;
import com.r7.core.assets.wallet.vo.CoreWalletVO;

/**
 * @author zs
 * @description: 钱包服务层
 * @date : 2020-10-26
 */
public interface CoreWalletService extends IService<CoreWallet> {


    /**
     * 新增钱包信息
     *
     * @param coreWalletDto 钱包传输实体
     * @param userId        操作者id
     * @return 返回是否成功
     */
    Boolean saveWallet(CoreWalletDTO coreWalletDto, Long userId);

    /**
     * 根据用户id修改钱包密码
     *
     * @param updateUserId      用户id
     * @param oldPayPassword    旧的密码
     * @param changePayPassword 修改的密码
     * @param userId            操作者id
     * @return 返回是否成功
     */
    Boolean updateWalletPayPasswordById(Long updateUserId, String oldPayPassword, String changePayPassword, Long userId);


    /**
     * 增加钱包总余额
     *
     * @param updateUserId               用户id
     * @param coreWalletBalanceChangeDto 新增钱包余额传输实体
     * @param userId                     操作者
     * @return 返回修改结果
     */
    CoreWalletVO updateCoreWalletAddBalance(Long updateUserId, CoreWalletBalanceChangeDTO coreWalletBalanceChangeDto, Long userId);

    /**
     * 增加钱包不可用余额
     *
     * @param updateUserId                      用户id
     * @param coreWalletLockingBalanceChangeDto 新增钱包不可用余额传输实体
     * @param userId                            操作者id
     * @return 返回查询结果
     */
    CoreWalletVO updateCoreWalletAddLockingBalance(
            Long updateUserId, CoreWalletLockingBalanceChangeDTO coreWalletLockingBalanceChangeDto, Long userId);

    /**
     * 减少钱包总余额
     *
     * @param updateUserId               用户id
     * @param coreWalletBalanceChangeDto 钱包减少总余额传输实体
     * @param userId                     操作者id
     * @return 返回修改结果
     */
    CoreWalletVO updateCoreWalletReduceBalance(
            Long updateUserId, CoreWalletBalanceChangeDTO coreWalletBalanceChangeDto, Long userId);

    /**
     * 减少钱包不可用余额
     *
     * @param updateUserId                      用户id
     * @param coreWalletLockingBalanceChangeDto 钱包减少不可用余额传输实体
     * @param userId                            操作者id
     * @return 返回修改结果
     */
    CoreWalletVO updateCoreWalletReduceLockingBalance(
            Long updateUserId, CoreWalletLockingBalanceChangeDTO coreWalletLockingBalanceChangeDto, Long userId);

    /**
     * 根据用户id查询钱包余额
     *
     * @param userId 用户id
     * @return 返回查询结果
     */
    CoreWalletVO getWalletByUserId(Long userId);

    /**
     * 根据用户id获取钱包总余额
     *
     * @param userId
     * @return 返回用户钱包总余额
     */
    Integer getWalletBalanceByUserId(Long userId);

    /**
     * 根据用户id查询钱包信息
     *
     * @param userId 用户id
     * @return 返回查询结果
     */
    CoreWallet getWalletChangeByUserId(Long userId);

    /**
     * 根据钱包id查找钱包余额
     *
     * @param id
     * @return 返回查询结果
     */
    CoreWalletVO getWalletById(Long id);

    /**
     * 统计全部用户总余额
     *
     * @return 返回全部用户总余额
     */
    Integer getTotalBalance();
}
