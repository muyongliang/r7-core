package com.r7.core.profit.service.impl;

import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.profit.constant.CalculationStatusEnum;
import com.r7.core.profit.constant.IncomeEnum;
import com.r7.core.profit.constant.SettlementEnum;
import com.r7.core.profit.dto.CoreRecordIncomeDTO;
import com.r7.core.profit.model.CoreProfit;
import com.r7.core.profit.service.CoreProfitService;
import com.r7.core.profit.service.CoreRecordIncomeService;
import com.r7.core.profit.service.SettlementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wt
 * @Description 定时核算实现类
 */
@Slf4j
@Service
public class SettlementServiceImpl implements SettlementService {

    @Autowired
    private CoreProfitService coreProfitService;

    @Autowired
    private CoreRecordIncomeService coreRecordIncomeService;

    @Override
    public int settlementAll(LocalDateTime endTime) {
        //扫描分润明细表，查询出需要核算哪些

        List<CoreProfit> list =  coreProfitService
                .getAllCoreProfitByStatus(CalculationStatusEnum.NOTCALCULATED, endTime);
        if (list == null  || list.size()==0) {
            return 1;
        }
        //核算截止时间以前且未核算过的所有分润明细
        //核算完成一个添加一条发放记录明细
        list.forEach(x -> {
            CoreRecordIncomeDTO coreRecordIncomeDTO = new CoreRecordIncomeDTO();
            Long recordIncomeId = SnowflakeUtil.getSnowflakeId();
            //判断是核算积分还是金额
            //1、单条核算
            if (x.getAmount() == 0  && x.getIntegral() !=0) {
                //核算积分
                List listIntegral =  coreProfitService
                        .settlementIntegral(x.getUserId(),x.getAppId(),CalculationStatusEnum.NOTCALCULATED
                        ,recordIncomeId,endTime);
             //设置发放记录的积分，分润条数
                coreRecordIncomeDTO.setDistributionIntegral((int) listIntegral.get(0));
                coreRecordIncomeDTO.setCountNum((int) listIntegral.get(1));
            }else if(x.getIntegral() == 0 && x.getAmount() != 0){
                //核算金额
                List listAmount =  coreProfitService
                        .settlementAmount(x.getUserId(),x.getAppId(),CalculationStatusEnum.NOTCALCULATED,
                        recordIncomeId, endTime);
                //设置发放记录的金额，分润条数
                coreRecordIncomeDTO.setDistributionAmount((int) listAmount.get(0));
                coreRecordIncomeDTO.setCountNum((int) listAmount.get(1));
            }else{
                throw  new BusinessException(SettlementEnum.SETTLEMENT_ERROR);
            }

            //2、核算完一条，添加一条发放记录

            coreRecordIncomeDTO.setAppId(x.getAppId());
            coreRecordIncomeDTO.setUserId(x.getUserId());
            coreRecordIncomeDTO.setStatus(IncomeEnum.NOTISSUED);
            coreRecordIncomeDTO.setDescription("已经核算,待发放");
            coreRecordIncomeService.saveCoreRecordIncome(recordIncomeId,coreRecordIncomeDTO,4L);


        });
        return 1;
    }
}
