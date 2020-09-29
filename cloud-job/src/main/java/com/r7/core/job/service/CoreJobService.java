package com.r7.core.job.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.job.dto.CoreJobDto;
import com.r7.core.job.dto.CoreJobStatusDto;
import com.r7.core.job.model.CoreJob;
import com.r7.core.job.vo.CoreJobVo;

import java.util.List;

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
    CoreJobVo saveJob(CoreJobDto coreJobDto, Long appId, Long userId);

    /**
     * 根据任务id进行修改
     * @param id 任务id
     * @param coreJobDto 任务实体
     * @param userId 操作人id
     * @return 任务修改结果
     */
    CoreJobVo updateJobById(Long id, CoreJobDto coreJobDto, Long userId);

    /**
     * 根据任务id修改任务状态
     * @param id 任务id
     * @param coreJobStatusDto 任务实体
     * @param userId 操作人id
     * @return 返回修改结果
     */
    CoreJobVo updateJobStatusById(Long id, CoreJobStatusDto coreJobStatusDto, Long userId);

    /**
     * 根據任务id查詢详情
     * @param id 任务id
     * @return 任务查询结果
     */
    CoreJobVo findJobById(Long id);

    /**
     * 分页查询
     * @param search 条件
     * @param pageNum 当前页
     * @param pageSize 页数
     * @return 查询分页结果
     */
    Page<CoreJobVo> pageJob(String search, Integer pageNum, Integer pageSize);

    /**
     * 分页查询当前模块任务及公共任务
     * @param appId appId
     * @param pageNum 当前页
     * @param pageSize 页数
     * @return 返回分页结果
     */
    Page<CoreJobVo> pageCurrentJob(Long appId, Long platformAppId, Integer pageNum, Integer pageSize);


}
