package com.r7.core.assets.funds.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.assets.funds.constant.CoreFundsErrorEnum;
import com.r7.core.assets.funds.dto.CoreFundsDTO;
import com.r7.core.assets.funds.mapper.CoreFundsMapper;
import com.r7.core.assets.funds.model.CoreFunds;
import com.r7.core.assets.funds.service.CoreFundsService;
import com.r7.core.assets.funds.vo.CoreFundsPageVO;
import com.r7.core.assets.funds.vo.CoreFundsVO;
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
 * @description: 资金服务实现层
 * @date : 2020-10-28
 */
@Slf4j
@Service
public class CoreFundsServiceImpl extends ServiceImpl<CoreFundsMapper, CoreFunds> implements CoreFundsService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveFunds(CoreFundsDTO coreFundsDto, Long appId, Long organId, Long userId) {
        Long fundsUserId = coreFundsDto.getUserId();
        log.info("在该平台:{}组织:{}中的用户:{}创建资金流动记录,操作者:{}", appId, organId, fundsUserId, userId);
        CoreFunds coreFunds = new CoreFunds();
        Long id = SnowflakeUtil.getSnowflakeId();
        coreFunds.setId(id);
        coreFunds.setAppId(appId);
        coreFunds.setOrganId(organId);
        coreFunds.toCoreFunds(coreFundsDto);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = dateFormat.format(new Date());
        Integer date = Integer.valueOf(format);
        coreFunds.setIncomeDate(date);
        coreFunds.setCreatedBy(userId);
        coreFunds.setCreatedAt(new Date());
        coreFunds.setUpdatedBy(userId);
        coreFunds.setUpdatedAt(new Date());
        boolean save = save(coreFunds);
        if (!save) {
            log.info("在该平台:{}组织:{}中的用户:{}创建资金流动记录失败,操作者:{}", appId, organId, fundsUserId, userId);
            throw new BusinessException(CoreFundsErrorEnum.FUNDS_SAVE_ERROR);
        }
        log.info("在该平台:{}组织:{}中的用户:{}创建资金流动记录成功,操作者:{}", appId, organId, fundsUserId, userId);
        return true;
    }

    @Override
    public CoreFundsVO getFundsById(Long id) {
        Option.of(id).getOrElseThrow(() -> new BusinessException(CoreFundsErrorEnum.FUNDS_ID_IS_NULL));
        CoreFunds coreFunds = Option.of(getOne(Wrappers.<CoreFunds>lambdaQuery()
                .select(CoreFunds::getId, CoreFunds::getAppId, CoreFunds::getInOrderSn,
                        CoreFunds::getUserId, CoreFunds::getOrganId, CoreFunds::getPayLink,
                        CoreFunds::getAmount, CoreFunds::getPayDate, CoreFunds::getStatus,
                        CoreFunds::getTransactionStatus, CoreFunds::getChannel, CoreFunds::getDescription)
                .eq(CoreFunds::getId, id)))
                .getOrElseThrow(() -> new BusinessException(CoreFundsErrorEnum.FUNDS_IS_NOT_EXISTS));
        return coreFunds.toCoreFundsVo();
    }

    @Override
    public IPage<CoreFundsPageVO> pageFundsByUserId(Long userId, Integer status, Integer transactionStatus, Integer channel, String startDate, String endDate, Integer pageNum, Integer pageSize) {
        Option.of(userId).getOrElseThrow(() -> new BusinessException(CoreFundsErrorEnum.FUNDS_USER_ID_IS_NULL));
        Page<CoreFundsPageVO> page = new Page<>(pageNum, pageSize);
        return baseMapper.pageFundsByUserId(userId, status, transactionStatus, channel, startDate, endDate, page);
    }

    @Override
    public IPage<CoreFundsPageVO> pageFundsByAppId(Long appId, Integer status, Integer transactionStatus, Integer channel, String startDate, String endDate, Integer pageNum, Integer pageSize) {
        Page<CoreFundsPageVO> page = new Page<>(pageNum, pageSize);
        return baseMapper.pageFundsByAppId(appId, status, transactionStatus, channel, startDate, endDate, page);
    }
}
