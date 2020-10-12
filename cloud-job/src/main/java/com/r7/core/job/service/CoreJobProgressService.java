package com.r7.core.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.job.dto.CoreJobProgressDTO;
import com.r7.core.job.model.CoreJobProgress;
import com.r7.core.job.vo.CoreJobProgressVO;

/**
 * @author zs
 * @description: 任务进度服务
 * @date : 2020-09-28
 */
public interface CoreJobProgressService extends IService<CoreJobProgress> {

    /**
     * 新增任务进度
     * @param coreJobProgressDto 新增任务进度信息
     * @param userId 操作人id
     * @return 返回新增结果
     */
    CoreJobProgressVO saveJobProgress(CoreJobProgressDTO coreJobProgressDto, Long userId);

    /**
     * 根据任务进度id修改任务进度
     * @param id 任务id
     * @param coreJobProgressDto 任务进度修改信息
     * @param userId 操作人id
     * @return 返回修改结果
     */
    CoreJobProgressVO updateJobById(Long id, CoreJobProgressDTO coreJobProgressDto, Long userId);

    /**
     * 根据用户id查询任务进度
     * @param userId 用户id
     * @return 返回查询结果
     */
    CoreJobProgressVO getJobProgressByUserId(Long userId);
}
