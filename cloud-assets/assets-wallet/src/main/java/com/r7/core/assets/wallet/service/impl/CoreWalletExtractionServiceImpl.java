package com.r7.core.assets.wallet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.assets.wallet.constant.WalletExtractionErrorEnum;
import com.r7.core.assets.wallet.dto.CoreWalletExtractionSaveDTO;
import com.r7.core.assets.wallet.dto.CoreWalletExtractionUpdateDTO;
import com.r7.core.assets.wallet.mapper.CoreWalletExtractionMapper;
import com.r7.core.assets.wallet.model.CoreWalletExtraction;
import com.r7.core.assets.wallet.service.CoreWalletExtractionService;
import com.r7.core.assets.wallet.vo.CoreWalletExtractionVO;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zs
 * @description: 钱包提现明细服务实现层
 * @date : 2020-10-26
 */
@Slf4j
@Service
public class CoreWalletExtractionServiceImpl
        extends ServiceImpl<CoreWalletExtractionMapper, CoreWalletExtraction> implements CoreWalletExtractionService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveWalletExtraction(CoreWalletExtractionSaveDTO coreWalletExtractionSaveDto,
                                        Long appId, Long organId, Long userId) {
        Long extractionUserId = coreWalletExtractionSaveDto.getUserId();
        String outOrderSn = coreWalletExtractionSaveDto.getOutOrderSn();
        log.info("组织:{}为中的用户:{}创建钱包提现明细,操作者:{}", organId, extractionUserId, userId);
        Option.of(extractionUserId).getOrElseThrow(() -> new BusinessException(WalletExtractionErrorEnum.WALLET_EXCEPTION_USER_ID_IS_NULL));
        int standard = 19;
        if (extractionUserId.toString().length() != standard) {
            throw new BusinessException(WalletExtractionErrorEnum.WALLET_EXCEPTION_USER_ID_LENGTH_INCORRECT);
        }
        if (outOrderSn.length() != standard) {
            throw new BusinessException(WalletExtractionErrorEnum.WALLET_EXCEPTION_OUT_ORDER_SN_LENGTH_IS_INCORRECT);
        }
        Long id = SnowflakeUtil.getSnowflakeId();
        CoreWalletExtraction coreWalletExtraction = new CoreWalletExtraction();
        coreWalletExtraction.setId(id);
        coreWalletExtraction.toCoreWalletExtraction(coreWalletExtractionSaveDto);
        String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
        Integer date = Integer.valueOf(format);
        coreWalletExtraction.setExtractionDate(date);
        coreWalletExtraction.setCreatedBy(userId);
        coreWalletExtraction.setCreatedAt(new Date());
        coreWalletExtraction.setUpdatedBy(userId);
        coreWalletExtraction.setUpdatedAt(new Date());
        boolean save = save(coreWalletExtraction);
        if (!save) {
            log.info("组织:{}为中的用户:{}创建钱包提现明细失败,操作者:{}", organId, extractionUserId, userId);
            throw new BusinessException(WalletExtractionErrorEnum.WALLET_EXTRACTION_SAVE_ERROR);
        }
        log.info("组织:{}为中的用户:{}创建钱包提现明细成功,操作者:{}", organId, extractionUserId, userId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoreWalletExtractionVO updateWalletExtractionById(Long id, CoreWalletExtractionUpdateDTO coreWalletExtractionUpdateDto,
                                                             Long appId, Long organId, Long userId) {
        Option.of(id).getOrElseThrow(() -> new BusinessException(WalletExtractionErrorEnum.WALLET_EXTRACTION_ID_IS_NULL));
        Long updateUserId = coreWalletExtractionUpdateDto.getUserId();
        int standard = 19;
        if (id.toString().length() != standard) {
            throw new BusinessException(WalletExtractionErrorEnum.WALLET_EXCEPTION_ID_LENGTH_IS_INCORRECT);
        }
        if (updateUserId.toString().length() != standard) {
            throw new BusinessException(WalletExtractionErrorEnum.WALLET_EXCEPTION_USER_ID_LENGTH_INCORRECT);
        }
        CoreWalletExtraction coreWalletExtraction = Option.of(getById(id))
                .getOrElseThrow(() -> new BusinessException(WalletExtractionErrorEnum.WALLET_EXTRACTION_ID_IS_NOT_EXISTS));
        Long extractionUserId = coreWalletExtraction.getUserId();
        log.info("平台:{}下组织:{},用户进行钱包提现:{}，操作者:{}", appId, organId, extractionUserId, userId);
        coreWalletExtraction.toCoreWalletExtraction(coreWalletExtractionUpdateDto);
        coreWalletExtraction.setUpdatedBy(userId);
        coreWalletExtraction.setUpdatedAt(new Date());
        boolean update = updateById(coreWalletExtraction);
        if (!update) {
            log.info("平台:{}下组织:{},用户进行钱包提现:{}失败，操作者:{}", appId, organId, extractionUserId, userId);
            throw new BusinessException(WalletExtractionErrorEnum.WALLET_EXTRACTION_UPDATE_ERROR);
        }
        log.info("平台:{}下组织:{},用户进行钱包提现:{}成功，操作者:{}", appId, organId, extractionUserId, userId);
        return coreWalletExtraction.toCoreWalletExtractionVo();
    }

    @Override
    public CoreWalletExtractionVO getWalletExtractionById(Long id) {
        Option.of(id).getOrElseThrow(() -> new BusinessException(WalletExtractionErrorEnum.WALLET_EXTRACTION_ID_IS_NULL));
        int standard = 19;
        if (id.toString().length() != standard) {
            throw new BusinessException(WalletExtractionErrorEnum.WALLET_EXCEPTION_ID_LENGTH_IS_INCORRECT);
        }
        CoreWalletExtraction coreWalletExtraction = Option.of(getById(id))
                .getOrElseThrow(() -> new BusinessException(WalletExtractionErrorEnum.WALLET_EXTRACTION_ID_IS_NOT_EXISTS));
        return coreWalletExtraction.toCoreWalletExtractionVo();
    }
}
