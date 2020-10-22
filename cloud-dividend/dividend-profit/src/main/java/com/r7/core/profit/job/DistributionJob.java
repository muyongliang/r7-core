package com.r7.core.profit.job;

import com.r7.core.common.exception.BusinessException;
import com.r7.core.profit.constant.DistributionEnum;
import com.r7.core.profit.model.CoreRecordIncome;
import com.r7.core.profit.service.CoreRecordIncomeService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wt
 * @Description 定时发放积分或者金额，定时任务来执行
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class DistributionJob  implements Job {
    @Autowired
    private CoreRecordIncomeService coreRecordIncomeService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //1、查询出所有发放状态为1的发放记录明细
        List<CoreRecordIncome> list=  coreRecordIncomeService.getAllCoreRecordIncomeByStatus(1);


        //2、调用钱包或积分接口进行金额或积分发放
        list.forEach(x ->{
            if (x.getDistributionAmount() != 0 && x.getDistributionIntegral() == 0) {
                //调用钱包接口发放金额

            }else if (x.getDistributionIntegral() != 0 && x.getDistributionAmount() == 0 ) {
                //调用积分接口发放积分

            }else {
                throw new BusinessException(DistributionEnum.DISTRIBUTION_ERROR);
            }
            //3、把发放记录明细的相关信息进行修改
            coreRecordIncomeService.updateCoreRecordIncomeStatusById(
                    x.getId(),
                    x.getAppId(),
                    x.getStatus(),
                    LocalDateTime.now(),
                    "已经发放",
                    //todo 需要考虑统计时跨天的问题
                    Integer.valueOf(new SimpleDateFormat( "yyyyMMdd")
                            .format(System.currentTimeMillis())),
                    0L
            );

        });
    }
}
