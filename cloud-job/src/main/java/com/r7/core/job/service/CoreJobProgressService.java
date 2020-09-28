package com.r7.core.job.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.job.dto.CoreJobProgressDto;
import com.r7.core.job.model.CoreJobProgress;
import com.r7.core.job.vo.CoreJobProgressVo;

/**
 * @author zs
 * @description: 任务进度服务
 * @date : 2020-09-28
 */
public interface CoreJobProgressService extends IService<CoreJobProgress> {

    /**
     * 插入Job
     * @param coreJobProgressDto
     * @param userId
     * @return
     */
    CoreJobProgressVo saveJobProgress(CoreJobProgressDto coreJobProgressDto, Long userId);

    /**
     * 根据用户id修改任务进度
     * @param id
     * @param coreJobProgressDto
     * @return
     */
    CoreJobProgressVo updateJobById(Long id, CoreJobProgressDto coreJobProgressDto, Long userId);

    /**
     * 根据用户id查询任务进度
     * @param userId
     * @return
     */
    CoreJobProgressVo getJobProgressByUserId(Long userId);
}
