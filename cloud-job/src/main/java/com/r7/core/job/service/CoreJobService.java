package com.r7.core.job.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.job.dto.CoreJobDTO;
import com.r7.core.job.dto.CoreJobStatusDTO;
import com.r7.core.job.model.CoreJob;
import com.r7.core.job.vo.CoreJobVO;

/**
 * 任务服务
 * @author zs
 */
public interface CoreJobService extends IService<CoreJob> {

    /**
     * 新增
     * @param coreJobDto 任务实体
     * @param userId 操作人id
     * @return 任务新增结果
     */
    CoreJobVO saveJob(CoreJobDTO coreJobDto, Long appId, Long userId);

    /**
     * 根据任务id进行修改
     * @param id 任务id
     * @param coreJobDto 任务实体
     * @param userId 操作人id
     * @return 任务修改结果
     */
    CoreJobVO updateJobById(Long id, CoreJobDTO coreJobDto, Long userId);

    /**
     * 根据任务id修改任务状态
     * @param id 任务id
     * @param coreJobStatusDto 任务实体
     * @param userId 操作人id
     * @return 返回修改结果
     */
    CoreJobVO updateJobStatusById(Long id, CoreJobStatusDTO coreJobStatusDto, Long userId);

    /**
     * 根據任务id查詢详情
     * @param id 任务id
     * @return 任务查询结果
     */
    CoreJobVO findJobById(Long id);

    /**
     * 分页查询
     * @param search 条件
     * @param pageNum 当前页
     * @param pageSize 页数
     * @return 查询分页结果
     */
    Page<CoreJobVO> pageJob(String search, Integer pageNum, Integer pageSize);

    /**
     * 分页查询当前模块任务及公共任务
     * @param appId appId
     * @param pageNum 当前页
     * @param pageSize 页数
     * @return 返回分页结果
     */
    Page<CoreJobVO> pageCurrentJob(Long appId, Long platformAppId, Integer pageNum, Integer pageSize);


}
