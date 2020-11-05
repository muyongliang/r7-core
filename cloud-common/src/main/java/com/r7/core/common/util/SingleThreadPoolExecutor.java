package com.r7.core.common.util;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author muyongliang
 * @date 2020/11/2
 * @description SingleThreadPoolExecutor
 */
public enum SingleThreadPoolExecutor {

    /**
     * 全局唯一实例
     */
    INSTANCE;
    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 20;
    /**
     * 最大线程数
     */
    private static final int MAXIMUM_POOL_SIZE = 50;
    /**
     * 空闲线程的存活时间
     */
    private static final int KEEP_ALIVE_TIME = 60;
    /**
     * 任务队列的大小
     */
    private static final int BLOCKING_QUEUE_SIZE = 100;
    /**
     * ThreadPoolExecutor
     */
    private ThreadPoolExecutor threadPoolExecutor;

    SingleThreadPoolExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(BLOCKING_QUEUE_SIZE),
                new ThreadFactoryBuilder().setNameFormat("single-thread-pool-%d").build(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public void execute(Runnable command) {
        this.threadPoolExecutor.execute(command);
    }
}
