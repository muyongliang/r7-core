package com.r7.core.trigger.service.impl;

import com.r7.core.common.exception.BusinessException;
import com.r7.core.trigger.config.MyCronJobListener;
import com.r7.core.trigger.constant.CoreQuartzJobErrorEnum;
import com.r7.core.trigger.dto.CoreQuartzJobInfoDTO;
import com.r7.core.trigger.service.CoreQuartzJobInfoService;
import com.r7.core.trigger.service.CoreQuartzJobService;
import com.r7.core.trigger.service.QuartzOptionalService;
import com.r7.core.trigger.vo.CoreQuartzJobVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.r7.core.trigger.config.QuartzConfig.*;

/**
 * @author wutao
 * @Description 定时任务的暂停、恢复、启动相关的操作实现类
 * @date 2020/9/29
 */

@Slf4j
@Service
public class QuartzOptionalServiceImpl implements QuartzOptionalService {
    @Autowired
    @Qualifier( "Scheduler")
    private Scheduler scheduler;

    @Autowired
    CoreQuartzJobService coreQuartzJobService;

    @Autowired
    CoreQuartzJobInfoService coreQuartzJobInfoService;

    @Override
    public int startCoreQuartzJob(Long id, Long userId) {

        //新添加的定时任务，才能初始化启动,其他的属于恢复
        log.info("定时任务ID：{}，操作内容：{}，操作人：{}，开始时间：{}", id,"初始化启动",userId,
                LocalDateTime.now());

        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_ID_IS_NULL));
        //根据id查询
        CoreQuartzJobVO coreQuartzJobVo = coreQuartzJobService.findCoreQuartzJobById(id);
        if (coreQuartzJobVo == null) {
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS);
        }
        String jobName = coreQuartzJobVo.getJobName();
        String jobGroup = coreQuartzJobVo.getJobGroup();
        String jobClass = coreQuartzJobVo.getJobClass();
        String cronExpression = coreQuartzJobVo.getRuleExpression();
       Integer misfire = coreQuartzJobVo.getMisfire();
        CoreQuartzJobInfoDTO coreQuartzJobInfoDto = new CoreQuartzJobInfoDTO();
//        CoreQuartzJobInfo coreQuartzJobInfo = new CoreQuartzJobInfo();
        //定时任务初始化启动
        try {
            JobDetail jobDetail = createJobDetail(jobName, jobGroup, jobClass);
            CronScheduleBuilder cron = createCronScheduleBuilder(cronExpression,misfire);
            Trigger trigger =  createTrigger(jobName, jobGroup,cron);
            //注册监听器
            scheduler.getListenerManager().addJobListener(new MyCronJobListener());
            scheduler.scheduleJob(jobDetail,trigger);
        } catch (SchedulerException e) {
            //修改定时任务的状态修改为异常(-1异常1待启动2正常3暂停)
            coreQuartzJobService.updateStatusById(id,-1,userId);
            //修改定时任务的描述为启动异常
            coreQuartzJobService.updateCoreQuartzJobDescriptionById(id,"属于启动异常",userId);
            //添加操作失败的操作记录

            e.printStackTrace();
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_START_ERROR);
        }
        //修改定时任务的状态修改为正常(-1异常1待启动2正常3暂停)
       coreQuartzJobService.updateStatusById(id,2,userId);
        //修改定时任务的初始启动时间
       coreQuartzJobService.updateCoreQuartzJobStartAtById(LocalDateTime.now(),id,userId);
        //添加操作成功的操作记录

        log.info("定时任务ID：{}，操作内容：{}，操作人：{}，结束时间：{}", id,"初始化启动",userId,
                LocalDateTime.now());
        return 1;
    }

    @Override
    public int pause(Long id, Long userId) {
        log.info("定时任务ID：{}，操作内容：{}，操作人：{}，开始时间：{}", id,"暂停",userId,
                LocalDateTime.now());
        //验证id是否为空
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_ID_IS_NULL));
        //根据id查询
        CoreQuartzJobVO coreQuartzJobVo = coreQuartzJobService.findCoreQuartzJobById(id);
        if (coreQuartzJobVo == null) {
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS);
        }
        String jobName = coreQuartzJobVo.getJobName();
        String jobGroup = coreQuartzJobVo.getJobGroup();

        CoreQuartzJobInfoDTO coreQuartzJobInfoDto = new CoreQuartzJobInfoDTO();
//        CoreQuartzJobInfo coreQuartzJobInfo = new CoreQuartzJobInfo();

        //执行暂停操作
        try {
            JobKey key = new JobKey(jobName, jobGroup);
            scheduler.pauseJob(key);
        } catch (SchedulerException e) {
            //修改定时任务的状态修改为异常(-1异常1待启动2正常3暂停)
            coreQuartzJobService.updateStatusById(id,1,userId);
            //添加操作失败的操作记录
            e.printStackTrace();
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_PAUSE_ERROR);
        }
        //修改定时任务的状态为暂停(-1异常1待启动2正常3暂停)
        coreQuartzJobService.updateStatusById(id,3,userId);

        //添加操作成功的操作记录
        log.info("定时任务ID：{}，操作内容：{}，操作人：{}，结束时间：{}", id,"暂停",userId,
                LocalDateTime.now());
        return 1;
    }

    @Override
    public int resume(Long id, Long userId) {
        log.info("定时任务ID：{}，操作内容：{}，操作人：{}，开始时间：{}", id,"恢复",userId,
                LocalDateTime.now());
        //验证id是否为空
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_ID_IS_NULL));
        //根据id查询
        CoreQuartzJobVO coreQuartzJobVo = coreQuartzJobService.findCoreQuartzJobById(id);
        if (coreQuartzJobVo == null) {
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS);
        }
        String jobName = coreQuartzJobVo.getJobName();
        String jobGroup = coreQuartzJobVo.getJobGroup();

        CoreQuartzJobInfoDTO coreQuartzJobInfoDto = new CoreQuartzJobInfoDTO();
//        CoreQuartzJobInfo coreQuartzJobInfo = new CoreQuartzJobInfo();
        //执行恢复操作
        try {
            JobKey key = new JobKey(jobName, jobGroup);
            scheduler.resumeJob(key);
        } catch (SchedulerException e) {
            //修改定时任务的状态修改为异常(-1异常1待启动2正常3暂停)
            coreQuartzJobService.updateStatusById(id,1,userId);
            //添加操作失败的操作记录
            e.printStackTrace();
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_RESUME_ERROR);
        }
        //修改定时任务的状态为正常(-1异常1待启动2正常3暂停)
        coreQuartzJobService.updateStatusById(id,2,userId);
        //修改定时任务的恢复时间
        coreQuartzJobService.updateCoreQuartzJobRegainAtById(LocalDateTime.now(),id,userId);
        //添加操作成功的操作记录
        log.info("定时任务ID：{}，操作内容：{}，操作人：{}，结束时间：{}", id,"恢复",userId,
                LocalDateTime.now());
        return 1;
    }

    @Override
    public int pauseAllJobs(Long userId, Long appId) {
        log.info("操作内容：{}，操作人：{}，开始时间：{}", "全部暂停",userId,
                LocalDateTime.now());

        CoreQuartzJobInfoDTO coreQuartzJobInfoDto = new CoreQuartzJobInfoDTO();
//        CoreQuartzJobInfo coreQuartzJobInfo = new CoreQuartzJobInfo();
        try {
            scheduler.pauseAll();
        } catch (SchedulerException e) {
            //添加操作失败的操作记录
            e.printStackTrace();
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_PAUSE_ALL_ERROR);
        }
        //把所有定时任务的状态修改为暂停(-1异常1待启动2正常3暂停)
        coreQuartzJobService.updateAllCoreQuartzJobStatus(3,userId);
        //添加操作成功的操作记录
        log.info("操作内容：{}，操作人：{}，结束时间：{}", "全部暂停",userId,
                LocalDateTime.now());


        return 1;
    }

    @Override
    public int resumeAllJobs(Long userId, Long appId) {
        log.info("操作内容：{}，操作人：{}，开始时间：{}", "全部恢复",userId,
                LocalDateTime.now());

        CoreQuartzJobInfoDTO coreQuartzJobInfoDto = new CoreQuartzJobInfoDTO();
//        CoreQuartzJobInfo coreQuartzJobInfo = new CoreQuartzJobInfo();
        try {
            scheduler.resumeAll();
        } catch (SchedulerException e) {

            //添加操作失败的操作记录
            e.printStackTrace();
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_RESUME_ALL_ERROR);
        }
        //把所有定时任务的状态修改为正常(-1异常1待启动2正常3暂停)
        coreQuartzJobService.updateAllCoreQuartzJobStatus(2,userId);
        //修改相应定时任务的恢复时间
        coreQuartzJobService.updateCoreQuartzJobAllRegainAt(LocalDateTime.now(),userId);

        //添加操作成功的操作记录
        log.info("操作内容：{}，操作人：{}，结束时间：{}", "全部恢复",userId,
                LocalDateTime.now());
        return 1;
    }

    @Override
    public int immediatePerform(Long id, Long userId) {

        log.info("定时任务ID：{}，操作内容：{}，操作人：{}，开始时间：{}", id,"立即执行",userId,
                LocalDateTime.now());

        CoreQuartzJobInfoDTO coreQuartzJobInfoDto = new CoreQuartzJobInfoDTO();
//        CoreQuartzJobInfo coreQuartzJobInfo = new CoreQuartzJobInfo();
        //验证id是否为空
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_ID_IS_NULL));
        //根据id查询
        CoreQuartzJobVO coreQuartzJobVo = coreQuartzJobService.findCoreQuartzJobById(id);
        if (coreQuartzJobVo == null) {
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS);
        }
        String jobName = coreQuartzJobVo.getJobName();
        String jobGroup = coreQuartzJobVo.getJobGroup();
        //执行立即执行操作
        try {
            JobKey key = new JobKey(jobName, jobGroup);
            scheduler.triggerJob(key);
            scheduler.resumeJob(key);
        } catch (SchedulerException e) {
            //修改定时任务的状态修改为异常(-1异常1待启动2正常3暂停)
            coreQuartzJobService.updateStatusById(id,-1,userId);
            //添加操作失败的操作记录
            e.printStackTrace();
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_IMMEDIATE_ERROR);
        }
        //修改定时任务状态为正常
        coreQuartzJobService.updateStatusById(id,2,userId);
        //添加操作成功的操作记录
        log.info("定时任务ID：{}，操作内容：{}，操作人：{}，结束时间：{}", id,"立即执行",userId,
                LocalDateTime.now());
        return 1;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int removeCoreQuartzJob(Long id, Long userId) {
        log.info("定时任务ID：{}，操作内容：{}，操作人：{}，开始时间：{}", id,"移除定时任务",userId,
                LocalDateTime.now());

        CoreQuartzJobInfoDTO coreQuartzJobInfoDto = new CoreQuartzJobInfoDTO();
//        CoreQuartzJobInfo coreQuartzJobInfo = new CoreQuartzJobInfo();
        //验证id是否为空
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_ID_IS_NULL));
        //根据id查询
        CoreQuartzJobVO coreQuartzJobVo = coreQuartzJobService.findCoreQuartzJobById(id);
        if (coreQuartzJobVo == null) {
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS);
        }
        String jobName = coreQuartzJobVo.getJobName();
        String jobGroup = coreQuartzJobVo.getJobGroup();
        //移除操作执行
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            //停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));
        } catch (Exception e) {
            //添加操作失败的操作记录
            e.printStackTrace();
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_REMOVE_ERROR);
        }
        //添加操作成功的操作记录

        log.info("定时任务ID：{}，操作内容：{}，操作人：{}，结束时间：{}", id,"定时任务已移除",userId,
                LocalDateTime.now());
        return 1;
    }
}
