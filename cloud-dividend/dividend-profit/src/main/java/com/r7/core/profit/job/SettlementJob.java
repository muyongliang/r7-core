package com.r7.core.profit.job;

import com.r7.core.profit.service.SettlementService;
import com.r7.core.trigger.job.Job1;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author wt
 * @Description 定时核算金额或者积分 定时任务来执行
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class SettlementJob implements Job {
    @Autowired
    private SettlementService settlementService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //定时核算
        settlementService.settlementAll(LocalDateTime.now());
    }
}
