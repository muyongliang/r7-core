package com.r7.core.assets.wallet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.assets.wallet.dto.CoreWalletDTO;
import com.r7.core.assets.wallet.dto.CoreWalletUpdateDTO;
import com.r7.core.assets.wallet.model.CoreWallet;
import com.r7.core.assets.wallet.vo.CoreWalletVO;

/**
 * @author zs
 * @description: 钱包服务层
 * @date : 2020-10-26
 */
public interface CoreWalletService extends IService<CoreWallet> {
    /**
     * 新增用户钱包信息
     *
     * @param coreWalletDto 钱包传输实体
     * @param appId         平台id
     * @param organId       组织id
     * @param userId        操作人id
     * @return 返回是否成功
     */
    Boolean saveWallet(CoreWalletDTO coreWalletDto, Long appId, Long organId, Long userId);


    /**
     * 根据用户id修改钱包余额
     *
     * @param userId              用户id
     * @param coreWalletUpdateDto 钱包修改传输实体
     * @param appId               平台id
     * @param organId             组织id
     * @return 返回是否成功
     */
    Boolean updateWalletByUserId(Long userId, CoreWalletUpdateDTO coreWalletUpdateDto, Long appId, Long organId);

    /**
     * 根据用户id查询钱包余额
     *
     * @param userId 用户id
     * @return 返回查询结果
     */
    CoreWalletVO getWalletByUserId(Long userId);

    /**
     * 分页查询钱包余额
     *
     * @param pageNum  当前页数
     * @param pageSize 每页展示个数
     * @return 返回查询结果
     */
    IPage<CoreWalletVO> pageWallet(Integer pageNum, Integer pageSize);

    /**
     * 查询全部总余额
     *
     * @return 返回查询结果
     */
    Integer getTotalBalance();
}
