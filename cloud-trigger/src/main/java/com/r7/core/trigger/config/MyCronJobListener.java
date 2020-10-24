package com.r7.core.trigger.config;

import com.r7.core.trigger.constant.MyExceptionEnum;
import com.r7.core.trigger.dto.CoreQuartzJobInfoDTO;
import com.r7.core.trigger.model.CoreQuartzJob;
import com.r7.core.trigger.service.CoreQuartzJobInfoService;
import com.r7.core.trigger.service.CoreQuartzJobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;

import java.time.LocalDateTime;


/**
 * @author wutao
 * @Description cronJob监听
 * @date 2020/9/30
 */

public class MyCronJobListener implements JobListener {
    private CoreQuartzJobService coreQuartzJobService =
            SpringJobBeanFactory.getBean(CoreQuartzJobService.class);

    private CoreQuartzJobInfoService coreQuartzJobInfoService =
            SpringJobBeanFactory.getBean(CoreQuartzJobInfoService.class);
    private LocalDateTime localDateTime;

    @Override
    public String getName() {
        return "myCronJobListener";
    }

    /**
     * 任务执行之前执行
     * @param context
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        //只是设置一个开始的时间
        localDateTime = LocalDateTime.now();
    }

    /**
     * 这个方法正常情况下不执行,但是如果当TriggerListener中的vetoJobExecution方法返回true时,那么执行这个方法.
     * 需要注意的是 如果方法(2)执行 那么(1),(3)这个俩个方法不会执行,因为任务被终止了嘛.
     * @param context
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("被否决执行了，可以做些日志记录。");
    }

    /**
     * 任务执行完成后执行,jobException如果它不为空则说明任务在执行过程中出现了异常
     * @param context
     * @param jobException
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        JobKey jobKey = context.getJobDetail().getKey();
        //根据定时任务的名字查询
        CoreQuartzJob coreQuartzJob =coreQuartzJobService.getCoreQuartzJobByJobName(jobKey.getName());
        CoreQuartzJobInfoDTO coreQuartzJobInfoDto = new CoreQuartzJobInfoDTO();
        coreQuartzJobInfoDto.setJobId(coreQuartzJob.getId());
        coreQuartzJobInfoDto.setAppId(coreQuartzJob.getAppId());
        //jobException如果它不为空则说明任务在执行过程中出现了异常
        if (jobException!=null && !(jobException.getMessage().contains(MyExceptionEnum.BUSINESSEXCEPTION))) {
            //执行情况的状态为异常，添加执行记录,如果把异常加在描述里，描述字段的类型就应该是大文本
            coreQuartzJobInfoDto.setDescription("执行中有异常");
            coreQuartzJobInfoDto.setStatus(2);
        }else{
            // 添加定时任务的执行记录，状态为正常
            coreQuartzJobInfoDto.setStatus(1);
        }

        //执行的开始时间和结束时间传过去
        //添加执行情况记录
        coreQuartzJobInfoService.saveCoreQuartzJobInfo(coreQuartzJobInfoDto,localDateTime,LocalDateTime.now());
        //统计执行的总次数和失败次数
        int count = coreQuartzJobInfoService.countCoreQuartzJob(coreQuartzJob.getId());
        int countFailure = coreQuartzJobInfoService.countCoreQuartzJobFailure(coreQuartzJob.getId());
        //修改执行的总次数和失败次数
        coreQuartzJobService.updateCoreQuartzJobCountAndCountFailure(coreQuartzJob.getId(),
                count,countFailure);
    }
}
