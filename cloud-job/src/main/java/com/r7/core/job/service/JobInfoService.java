package com.r7.core.job.service;

import com.r7.core.job.model.CoreJob;

import java.util.List;

/**
 * @author zs
 */
public interface JobInfoService {
    /**
     * 添加
     * @param coreJob
     */
    void add(CoreJob coreJob);

    /**
     * 刪除
     * @param id
     */
    void delete(Long id);

    /**
     * 修改
     * @param coreJob
     */
    void update(CoreJob coreJob);

    /**
     * 根據id查詢
     * @param id
     * @return
     */
    CoreJob findById(Long id);

    /**
     * 查詢全部
     * @return
     */
    List<CoreJob> findAll();
}
