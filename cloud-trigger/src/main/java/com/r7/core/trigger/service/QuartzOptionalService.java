package com.r7.core.trigger.service;


/**
 * @author wutao
 * @Description 定时任务的暂停、恢复、启动相关的操作
 * @date 2020/9/29
 */
public interface QuartzOptionalService {

    /**
     * 根据定时任务id初始化启动
     * @param id 定时任务id
     * @param userId 操作者
     * @return 返回启动结果
     */
     int  startCoreQuartzJob (Long id, Long userId);

    /**
     * 根据定时任务id暂停定时任务
     * @param id 定时任务id
     * @param userId 操作者
     * @return 返回暂停结果
     */
    int pause(Long id ,Long userId);

    /**
     * 根据定时任务id恢复定时任务
     * @param id 定时任务id
     * @param userId 操作者
     * @return 返回恢复结果
     */
    int resume(Long id ,Long userId);

    /**
     * 暂停所有定时任务
     * @param userId 操作者
     * @param appId 平台id
     * @return 返回暂停结果
     */
    int  pauseAllJobs(Long userId, Long appId);

    /**
     * 恢复所有定时任务
     * @param userId 操作者
     * @param appId  平台id
     * @return 返回恢复结果
     */
    int  resumeAllJobs(Long userId, Long appId);

    /**
     * 立即执行某个定时任务
     * @param id 定时任务id
     * @param userId 操作者id
     * @return 返回立即执行结果
     */
    int immediatePerform(Long id ,Long userId);

    /**
     * 移除定时任务
     * @param id 定时任务id
     * @param userId 操作者
     * @return 返回移除结果
     */
    int removeCoreQuartzJob(Long id ,Long userId);
}
