package com.r7.core.job.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.job.dto.CoreJobDto;
import com.r7.core.job.model.CoreJob;
import com.r7.core.job.vo.CoreJobVo;

import java.util.List;

/**
 * 任务服务
 * @author zs
 */
public interface CoreJobService extends IService<CoreJob> {

    /**
     * 插入
     * @param coreJobDto 任务实体
     * @param userId 操作人id
     * @return CoreJobVo
     */
    CoreJobVo saveJob(Long appId, CoreJobDto coreJobDto, Long userId);

    /**
     * 删除
     * @param id 任务id
     * @param userId 操作人id
     * @return
     */
    boolean removeJobById(Long id, Long userId);

    /**
     * 根据任务id进行修改
     * @param id 任务id
     * @param coreJobDto 任务实体
     * @param userId 操作人id
     * @return
     */
    CoreJobVo updateJobById(Long id, CoreJobDto coreJobDto, Long userId);

    /**
     * 根據id查詢
     * @param id 任务id
     * @return 人数详情
     */
    CoreJobVo findJobById(Long id);

    /**
     * 分页查询
     * @param status 状态
     * @param jobName 任务名
     * @param pageNum 当前页
     * @param pageSize 页数
     * @return page
     */
    Page<CoreJobVo> pageJob(Integer status, String jobName, Integer pageNum, Integer pageSize);

    /**
     * 分页查询当前模块任务及公共任务
     * @param appId appId
     * @param pageNum 当前页
     * @param pageSize 页数
     * @return page
     */
    Page<CoreJobVo> pageCurrentJob(Long appId, Integer pageNum, Integer pageSize);


}
