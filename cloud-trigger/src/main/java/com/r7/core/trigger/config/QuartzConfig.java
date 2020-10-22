package com.r7.core.trigger.config;

import com.r7.core.trigger.constant.CoreQuartzJobErrorEnum;
import com.r7.core.trigger.constant.MisfireEnum;
import org.quartz.*;


/**
 * 生成job实例和触发器的工具类
 *
 * @author wutao
 * @date 2020/9/24
 */
public  class QuartzConfig {
    /**
     * 获得job实例
     * @param jobName 作业名称
     * @param jobGroup 作业分组
     * @param jobClass 作业路径
     * @return 作业实例
     */
    public static JobDetail createJobDetail(String jobName,String jobGroup, String jobClass) {
        //根据类路径得到job类模板
            Class classTemplate = null;
            try {
                classTemplate =  Class.forName(jobClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        //根据类模板创建job实例
        JobDetail job = JobBuilder.newJob(classTemplate)
                .withIdentity(jobName, jobGroup)
                .requestRecovery()//scheduler发生硬关闭重启后会被重新执行，如运行关闭或关机
                .build();
        return job;
    }

    /**
     *  根据corn表达式创建表达式调度构建器
     * @param cronExpression corn表达式
     * @return 表达式调度构建器
     */
    public static CronScheduleBuilder createCronScheduleBuilder(String cronExpression ,
                                                                Integer misfire){
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                .cronSchedule(cronExpression);
        //错失执行策略
        if (misfire == MisfireEnum.MISFIRE1) {
            //下周期再执行
            cronScheduleBuilder.withMisfireHandlingInstructionDoNothing();
        } else if (misfire == MisfireEnum.MISFIRE2){
            //以当前时间为触发频率立刻触发一次执行，然后按照Cron频率依次执行
            cronScheduleBuilder.withMisfireHandlingInstructionFireAndProceed();
        }else if(misfire == MisfireEnum.MISFIRE3){
            //错失周期立即执行,重做错失的所有频率
            //当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
            cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
        }


        return cronScheduleBuilder;
    }

    /**
     *  创建触发器
     * @param jobName 任务名
     * @param jobGroup 任务分组
     * @param cronScheduleBuilder cron表达式调度构建器
     * @return 触发器
     */
    public static Trigger createTrigger(String jobName, String jobGroup,CronScheduleBuilder cronScheduleBuilder){
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, jobGroup)
                .withSchedule(cronScheduleBuilder)
                .build();
        return trigger;
    }

}
