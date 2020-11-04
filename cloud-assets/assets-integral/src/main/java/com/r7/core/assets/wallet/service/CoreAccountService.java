package com.r7.core.assets.wallet.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.assets.wallet.dto.CoreAccountDTO;
import com.r7.core.assets.wallet.model.CoreAccount;
import com.r7.core.assets.wallet.vo.CoreAccountVO;

import java.util.List;

/**
 * @author zs
 * @description: 用户账户服务层
 * @date : 2020-10-28
 */
public interface CoreAccountService extends IService<CoreAccount> {

    /**
     * 新增用户账户
     *
     * @param coreAccountDto 用户账户传输实体
     * @param appId          平台id
     * @param organId        组织id
     * @param userId         操作者id
     * @return 返回是否成功
     */
    Boolean saveCoreAccount(CoreAccountDTO coreAccountDto, Long appId, Long organId, Long userId);

    /**
     * 根据用户id查询用户账户
     *
     * @param userId 用户id
     * @return 返回查询结果
     */
    List<CoreAccountVO> listAccountByUserId(Long userId);
}
