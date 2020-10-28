package com.r7.core.assets.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.assets.constant.CoreWalletErrorEnum;
import com.r7.core.assets.dto.CoreWalletBillDTO;
import com.r7.core.assets.mapper.CoreWalletBillMapper;
import com.r7.core.assets.model.CoreWalletBill;
import com.r7.core.assets.service.CoreWalletBillService;
import com.r7.core.assets.vo.CoreWalletBillPageVO;
import com.r7.core.assets.vo.CoreWalletBillVO;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    @Override
    public Boolean saveWalletBill(CoreWalletBillDTO coreWalletBillDto, Long appId, Long organId, Long userId) {
        Long billUserId = coreWalletBillDto.getUserId();
        log.info("平台:{}对组织:{}中的用户:{}创建了钱包账单,操作人:{}。", appId, organId, billUserId, userId);
        Long id = SnowflakeUtil.getSnowflakeId();
        CoreWalletBill coreWalletBill = new CoreWalletBill();
        coreWalletBill.setId(id);
        coreWalletBill.setAppId(appId);
        coreWalletBill.setOrganId(organId);
        coreWalletBill.toCoreWalletBill(coreWalletBillDto);
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
            throw new BusinessException(CoreWalletErrorEnum.WALLET_BILL_SAVE_ERROR);
        }
        log.info("平台:{}对组织:{}中的用户:{}创建了钱包账单成功,操作人:{}。", appId, organId, billUserId, userId);
        return true;
    }

    @Override
    public CoreWalletBillVO updateWalletBillById(Long id, CoreWalletBillDTO coreWalletBillDto, Long appId, Long organId, Long userId) {
        log.info("平台:{}对组织:{}中的钱包账单:{}进行修改,操作人:{}。", appId, organId, id, userId);
        id = Option.of(id).getOrElseThrow(() -> new BusinessException(CoreWalletErrorEnum.WALLET_BILL_ID_IS_NULL));
        CoreWalletBill coreWalletBill = Option.of(
                baseMapper.selectById(id)).getOrElseThrow(() -> new BusinessException(CoreWalletErrorEnum.WALLET_BILL_IS_NOT_EXISTS));
        coreWalletBill.toCoreWalletBill(coreWalletBillDto);
        coreWalletBill.setUpdatedBy(userId);
        coreWalletBill.setUpdatedAt(new Date());
        boolean update = updateById(coreWalletBill);
        if (!update) {
            log.info("平台:{}对组织:{}中的钱包账单:{}进行修改失败,操作人:{}。", appId, organId, id, userId);
            throw new BusinessException(CoreWalletErrorEnum.WALLET_BILL_UPDATE_ERROR);
        }
        log.info("平台:{}对组织:{}中的钱包账单:{}进行修改完成,操作人:{}。", appId, organId, id, userId);
        return coreWalletBill.toCoreWalletBillVo();
    }

    @Override
    public CoreWalletBillVO getWalletBillById(Long id) {
        CoreWalletBillVO coreWalletBillVo = new CoreWalletBillVO();
        CoreWalletBill coreWalletBill = baseMapper.selectById(id);
        BeanUtils.copyProperties(coreWalletBill, coreWalletBillVo);
        return coreWalletBillVo;
    }

    @Override
    public IPage<CoreWalletBillPageVO> pageWalletBill(Long userId, Integer pageNum, Integer pageSize) {
        Page<CoreWalletBillPageVO> page = new Page<>(pageNum, pageSize);
        return baseMapper.pageWalletBill(userId, page);
    }
}
