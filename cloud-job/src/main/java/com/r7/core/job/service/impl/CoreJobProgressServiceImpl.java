package com.r7.core.job.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.job.constant.JobErrorEnum;
import com.r7.core.job.dto.CoreJobProgressDto;
import com.r7.core.job.mapper.CoreJobProgressMapper;
import com.r7.core.job.model.CoreJobProgress;
import com.r7.core.job.service.CoreJobProgressService;
import com.r7.core.job.vo.CoreJobProgressVo;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zs
 * @description:
 * @date : 2020-09-28
 */
@Slf4j
@Service
public class CoreJobProgressServiceImpl
        extends ServiceImpl<CoreJobProgressMapper, CoreJobProgress>
        implements CoreJobProgressService {

    @Resource
    private CoreJobProgressMapper coreJobProgressMapper;

    @Override
    public CoreJobProgressVo saveJobProgress(CoreJobProgressDto coreJobProgressDto, Long userId) {
        log.info("新增用户任务进度：{}，操作人：{}，开始时间：{}", coreJobProgressDto, 666L, new Date());
        CoreJobProgress coreJobProgress = new CoreJobProgress();
        coreJobProgress.toCoreJobProgress(coreJobProgressDto);
//        coreJobProgress.setIsDistribution(0);
        coreJobProgress.setStartAt(new Date());
        coreJobProgress.setCreatedBy(666L);
        coreJobProgress.setCreatedAt(new Date());
        coreJobProgress.setUpdatedBy(666L);
        coreJobProgress.setUpdatedAt(new Date());
        baseMapper.insert(coreJobProgress);
        log.info("新增用户任务进度：{}成功，开始时间：{}", coreJobProgressDto, 666L, new Date());
        return coreJobProgress.toCoreJobProgressVo();
    }

    @Override
    public CoreJobProgressVo updateJobById(Long id, CoreJobProgressDto coreJobProgressDto, Long userId) {
        log.info("修改用户任务进度：{}，修改内容：{}", id, coreJobProgressDto);
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(JobErrorEnum.JOB_PROGRESS_ID_IS_NULL));
        CoreJobProgress coreJobProgress = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(JobErrorEnum.JOB_PROGRESS_IS_NOT_EXISTS));
        coreJobProgress.toCoreJobProgress(coreJobProgressDto);
        coreJobProgress.setUpdatedBy(66L);
        coreJobProgress.setUpdatedAt(new Date());
        baseMapper.updateById(coreJobProgress);
        log.info("修改用户任务进度：{}，完成", id, coreJobProgressDto);
        return coreJobProgress.toCoreJobProgressVo();
    }

    @Override
    public CoreJobProgressVo getJobProgressByUserId(Long userId) {
        CoreJobProgress coreJobProgress = coreJobProgressMapper.getJobProgressByUserId(userId);
         return coreJobProgress.toCoreJobProgressVo();
    }
}
