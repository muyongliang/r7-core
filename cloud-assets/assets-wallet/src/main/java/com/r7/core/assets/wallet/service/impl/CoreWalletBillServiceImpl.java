package com.r7.core.assets.wallet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.assets.wallet.constant.WalletBillStatusEnum;
import com.r7.core.assets.wallet.constant.WalletBillTypeEnum;
import com.r7.core.assets.wallet.constant.WalletErrorEnum;
import com.r7.core.assets.wallet.dto.CoreWalletBillDTO;
import com.r7.core.assets.wallet.mapper.CoreWalletBillMapper;
import com.r7.core.assets.wallet.model.CoreWalletBill;
import com.r7.core.assets.wallet.service.CoreWalletBillService;
import com.r7.core.assets.wallet.service.CoreWalletService;
import com.r7.core.assets.wallet.vo.CoreWalletBillPageVO;
import com.r7.core.assets.wallet.vo.CoreWalletBillVO;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zs
 * @description: 钱包账单服务实现层
 * @date : 2020-10-26
 */
@Slf4j
@Service
public class CoreWalletBillServiceImpl
        extends ServiceImpl<CoreWalletBillMapper, CoreWalletBill> implements CoreWalletBillService {

    @Resource
    private CoreWalletService coreWalletService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveWalletBill(CoreWalletBillDTO coreWalletBillDto, Long appId, Long organId, Long userId) {
        Long billUserId = coreWalletBillDto.getUserId();
        log.info("平台:{}对组织:{}中的用户:{}创建了钱包账单,操作人:{}。", appId, organId, billUserId, userId);

        Integer balance = coreWalletService.getWalletBalanceByUserId(billUserId);

        Long id = SnowflakeUtil.getSnowflakeId();
        CoreWalletBill coreWalletBill = new CoreWalletBill();
        coreWalletBill.setId(id);
        coreWalletBill.setAppId(appId);
        coreWalletBill.setOrganId(organId);
        coreWalletBill.toCoreWalletBill(coreWalletBillDto);
        coreWalletBill.setBalance(balance);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = dateFormat.format(new Date());
        Integer date = Integer.valueOf(format);
        coreWalletBill.setTransactionDate(date);
        coreWalletBill.setCreatedBy(userId);
        coreWalletBill.setCreatedAt(new Date());
        coreWalletBill.setUpdatedBy(userId);
        coreWalletBill.setUpdatedAt(new Date());
        boolean save = save(coreWalletBill);
        if (!save) {
            log.info("平台:{}对组织:{}中的用户:{}创建了钱包账单失败,操作人:{}。", appId, organId, billUserId, userId);
            throw new BusinessException(WalletErrorEnum.WALLET_BILL_SAVE_ERROR);
        }
        log.info("平台:{}对组织:{}中的用户:{}创建了钱包账单成功,操作人:{}。", appId, organId, billUserId, userId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoreWalletBillVO updateWalletBillById(Long id, CoreWalletBillDTO coreWalletBillDto, Long appId, Long organId, Long userId) {
        log.info("平台:{}对组织:{}中的钱包账单:{}进行修改,操作人:{}。", appId, organId, id, userId);
        id = Option.of(id).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_BILL_ID_IS_NULL));
        CoreWalletBill coreWalletBill = Option.of(
                baseMapper.selectById(id)).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_BILL_IS_NOT_EXISTS));
        coreWalletBill.toCoreWalletBill(coreWalletBillDto);
        coreWalletBill.setUpdatedBy(userId);
        coreWalletBill.setUpdatedAt(new Date());
        boolean update = updateById(coreWalletBill);
        if (!update) {
            log.info("平台:{}对组织:{}中的钱包账单:{}进行修改失败,操作人:{}。", appId, organId, id, userId);
            throw new BusinessException(WalletErrorEnum.WALLET_BILL_UPDATE_ERROR);
        }
        log.info("平台:{}对组织:{}中的钱包账单:{}进行修改完成,操作人:{}。", appId, organId, id, userId);
        return coreWalletBill.toCoreWalletBillVo();
    }

    @Override
    public CoreWalletBillVO getWalletBillById(Long id) {
        Option.of(id).getOrElseThrow(() -> new BusinessException(WalletErrorEnum.WALLET_BILL_ID_IS_NULL));
        CoreWalletBillVO coreWalletBillVo = new CoreWalletBillVO();
        CoreWalletBill coreWalletBill = baseMapper.selectById(id);
        BeanUtils.copyProperties(coreWalletBill, coreWalletBillVo);
        return coreWalletBillVo;
    }

    @Override
    public IPage<CoreWalletBillPageVO> pageWalletBillByUserId(Long userId, WalletBillTypeEnum type, String source, WalletBillStatusEnum status,
                                                              String startDate, String endDate, Integer pageNum, Integer pageSize) {
        Page<CoreWalletBillPageVO> page = new Page<>(pageNum, pageSize);
        Integer typeValue = type.getValue();
        Integer statusValue = status.getValue();
        return baseMapper.pageWalletBillByUserId(userId, typeValue, source, statusValue, startDate, endDate, page);
    }

    @Override
    public IPage<CoreWalletBillPageVO> pageWalletBillByAppId(Long appId, WalletBillTypeEnum type, String source, WalletBillStatusEnum status,
                                                             String startDate, String endDate, Integer pageNum, Integer pageSize) {
        Page<CoreWalletBillPageVO> page = new Page<>(pageNum, pageSize);
        Integer typeValue = type.getValue();
        Integer statusValue = status.getValue();
        return baseMapper.pageWalletBillByAppId(appId, typeValue, source, statusValue, startDate, endDate, page);
    }

    @Override
    public Integer getTotalConsumptionBalance(Long appId, String startDate, String endDate) {
        Integer totalConsumptionBalance = baseMapper.getTotalConsumptionBalance(appId, startDate, endDate);
        return totalConsumptionBalance;
    }
}
