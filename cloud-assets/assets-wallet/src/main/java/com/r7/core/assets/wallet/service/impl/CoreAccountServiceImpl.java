package com.r7.core.assets.wallet.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.assets.wallet.constant.AccountErrorEnum;
import com.r7.core.assets.wallet.dto.CoreAccountDTO;
import com.r7.core.assets.wallet.mapper.CoreAccountMapper;
import com.r7.core.assets.wallet.model.CoreAccount;
import com.r7.core.assets.wallet.service.CoreAccountService;
import com.r7.core.assets.wallet.vo.CoreAccountVO;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zs
 * @description: 用户账户服务实现层
 * @date : 2020-10-28
 */
@Slf4j
@Service
public class CoreAccountServiceImpl extends ServiceImpl<CoreAccountMapper, CoreAccount> implements CoreAccountService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveCoreAccount(CoreAccountDTO coreAccountDto, Long appId, Long organId, Long userId) {
        Long accountUserId = coreAccountDto.getUserId();
        log.info("平台:{}下组织:{}中用户:{}新增账户,操作者:{}", appId, organId, accountUserId, userId);
        Option.of(accountUserId).getOrElseThrow(() -> new BusinessException(AccountErrorEnum.ACCOUNT_USER_ID_IS_NULL));
        int standard = 19;
        if (accountUserId.toString().length() != standard) {
            throw new BusinessException(AccountErrorEnum.ACCOUNT_USER_ID_LENGTH_IS_INCORRECT);
        }
        Long id = SnowflakeUtil.getSnowflakeId();
        CoreAccount coreAccount = new CoreAccount();
        coreAccount.setId(id);
        coreAccount.setAppId(appId);
        coreAccount.setOrganId(organId);
        coreAccount.toCoreAccount(coreAccountDto);
        coreAccount.setCreatedBy(userId);
        coreAccount.setCreatedAt(new Date());
        coreAccount.setUpdatedBy(userId);
        coreAccount.setUpdatedAt(new Date());
        boolean save = save(coreAccount);
        if (!save) {
            log.info("平台:{}下组织:{}中用户:{}新增账户失败,操作者:{}", appId, organId, accountUserId, userId);
            throw new BusinessException(AccountErrorEnum.ACCOUNT_SAVE_ERROR);
        }
        log.info("平台:{}下组织:{}中用户:{}新增账户成功,操作者:{}", appId, organId, accountUserId, userId);
        return true;
    }

    @Override
    public List<CoreAccountVO> listAccountByUserId(Long userId) {
        Option.of(userId).getOrElseThrow(() -> new BusinessException(AccountErrorEnum.ACCOUNT_USER_ID_IS_NULL));
        int standard = 19;
        if (userId.toString().length() != standard) {
            throw new BusinessException(AccountErrorEnum.ACCOUNT_USER_ID_LENGTH_IS_INCORRECT);
        }
        List<CoreAccount> accountList = Option.of(list(Wrappers.<CoreAccount>lambdaQuery()
                .select(CoreAccount::getId, CoreAccount::getAppId, CoreAccount::getOrganId,
                        CoreAccount::getUserId, CoreAccount::getAccount, CoreAccount::getChannel,
                        CoreAccount::getType)
                .eq(CoreAccount::getUserId, userId)))
                .getOrElseThrow(() -> new BusinessException(AccountErrorEnum.USER_ID_IS_NOT_EXISTS));
        if (accountList == null || accountList.size() == 0) {
            return null;
        }
        return accountList.stream().map(x -> {
            CoreAccountVO coreAccountVo = x.toCoreAccountVo();
            return coreAccountVo;
        }).collect(Collectors.toList());
    }
}
