package com.r7.core.profit.job;

import com.r7.core.profit.service.SettlementService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author wt
 * @Description 定时核算金额或者积分 定时任务来执行
 * XxlJob开发示例（Bean模式，基于方法）
 *
 * 开发步骤：
 * 1、在Spring Bean实例中，开发Job方法，方式格式要求为 "public ReturnT<String> execute(String param)"
 * 2、为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称",
 * init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，
 * 注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 */
@Slf4j
@Component
public class SettlementJob {
    @Autowired
    private SettlementService settlementService;

    /**
     * 1、定时核算（Bean模式）
     */
    @XxlJob("Settlement")
    public ReturnT<String> settlement(String param) throws Exception {

        //只有用XxlJobLogger.log打印日志才会出现在执行日志中
        XxlJobLogger.log("XXL-JOB, Hello World.");

        //定时核算
        XxlJobLogger.log("开始时间:{}",LocalDateTime.now());
        settlementService.settlementAll(LocalDateTime.now());

        return ReturnT.SUCCESS;
    }

}
