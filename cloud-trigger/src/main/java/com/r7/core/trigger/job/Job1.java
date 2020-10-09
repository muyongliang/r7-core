package com.r7.core.trigger.job;

import org.quartz.*;
import java.text.SimpleDateFormat;

/**
 * 标准作业实例
 *
 * @author wutao
 * @date 2020/9/25
 **@DisallowConcurrentExecution 该注解是告诉quartz不要并发执行同一个job实例即jobDetail
 * @PersistJobDataAfterExecution 该注解不是必须的，是告诉quartz成功执行execute方法后更新JobDataMap中的数据
 * 有这个@PersistJobDataAfterExecution注解一般就要有@DisallowConcurrentExecution来保证数据的安全
 * execute方法中仅允许抛出一种类型的异常（包括RuntimeExceptions），即JobExecutionException
 * 注意：如果需要 JobDataMap存放写额外数据需要job实例实现Serializable
 * cron表达式在线生成工具地址：https://cron.qqe2.com/
 *cron表达式0/2 * * * * ? 从左到右分别表示：秒、分、时、日、月、星期、年
 * 星期一般可以用？占位符代替，也可以指定
 * cron表达式例子："0 0 12 * * ?" 每天中午12点触发
 * "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
 * misfire错失执行策略：错失策略 1下周期再执行/2错失周期执行一次/3错失周期立即执行
 *
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class Job1 implements Job {



    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
       //如果需要设置job的额外属性加上这两行代码，如果没有额外属性这两行代码就不是必须的
        //并加上该类的无参构造方法和额外属性的get/set方法，并设置成成员变量
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        //以下是job具体执行的业务逻辑
        System.out.println("开始："+new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")
                .format(System.currentTimeMillis()));
        System.out.println("你好我是job1");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束："+new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")
                .format(System.currentTimeMillis()));
    }
}
