package com.r7.core.trigger.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.trigger.dto.CoreQuartzJobInfoDTO;
import com.r7.core.trigger.model.CoreQuartzJobInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.trigger.vo.CoreQuartzJobInfoVO;

import java.time.LocalDateTime;

/**
 * 
 * @Description 定时任务执行情况
 * @author wutao
 * @date 2020/9/28
 */
public interface CoreQuartzJobInfoService extends IService<CoreQuartzJobInfo>{
    /**
     * 添加定时任务操作记录
     * @param coreQuartzJobInfoDto 定时任务执行情况传输层
     * @param startAt 作业的开始时间
     * @param endAt   作业的结束时间
     * @return 返回添加的结果
     */
    CoreQuartzJobInfoVO saveCoreQuartzJobInfo(CoreQuartzJobInfoDTO coreQuartzJobInfoDto,
                                              LocalDateTime startAt , LocalDateTime endAt);
    /**
     * 根据定时任务id，查询操作记录
     * @param jobId 定时任务id
     * @param pageNum 当前页
     * @param pageSize 每页显示数
     * @return 返回查询结果
     */
    Page<CoreQuartzJobInfoVO> pageCoreQuartzJob(Long jobId,
                                                Integer pageNum, Integer pageSize);


    /**
     * 统计定时任务的执行次数
     * @param id 定时任务id
     * @return 返回统计结果
     */
    int countCoreQuartzJob(Long id);

    /**
     * 统计定时任务的执行失败的次数
     * @param id 定时任务id
     * @return 返回的定时任务执行失败的次数
     */
    int countCoreQuartzJobFailure(Long id);
}
