package com.r7.core.job.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.job.constant.JobErrorEnum;
import com.r7.core.job.dto.CoreJobProgressDTO;
import com.r7.core.job.mapper.CoreJobProgressMapper;
import com.r7.core.job.model.CoreJobProgress;
import com.r7.core.job.service.CoreJobProgressService;
import com.r7.core.job.vo.CoreJobProgressVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zs
 * @description: 任务进度实现层
 * @date : 2020-09-28
 */
@Slf4j
@Service
public class CoreJobProgressServiceImpl
        extends ServiceImpl<CoreJobProgressMapper, CoreJobProgress>
        implements CoreJobProgressService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoreJobProgressVO saveJobProgress(CoreJobProgressDTO coreJobProgressDto, Long userId) {
        log.info("新增用户任务进度：{}，操作人：{}，开始时间：{}", coreJobProgressDto, userId, new Date());
        CoreJobProgress coreJobProgress = new CoreJobProgress();
        coreJobProgress.toCoreJobProgress(coreJobProgressDto);
        Long id = SnowflakeUtil.getSnowflakeId();
        coreJobProgress.setId(id);
        coreJobProgress.setUserId(userId);
        coreJobProgress.setStartAt(new Date());
        coreJobProgress.setCreatedBy(userId);
        coreJobProgress.setCreatedAt(new Date());
        coreJobProgress.setUpdatedBy(userId);
        coreJobProgress.setUpdatedAt(new Date());
        baseMapper.insert(coreJobProgress);
        log.info("新增用户任务进度传输实体：{}成功，操作人:{},操作时间：{}", coreJobProgressDto, userId, new Date());
        return coreJobProgress.toCoreJobProgressVo();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoreJobProgressVO updateJobById(Long id, CoreJobProgressDTO coreJobProgressDto, Long userId) {
        log.info("修改用户任务进度：{}，操作人：{}，修改内容：{}", id, userId, coreJobProgressDto);
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(JobErrorEnum.JOB_PROGRESS_ID_IS_NULL));
        CoreJobProgress coreJobProgress = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(JobErrorEnum.JOB_PROGRESS_IS_NOT_EXISTS));
        coreJobProgress.toCoreJobProgress(coreJobProgressDto);
        coreJobProgress.setUpdatedBy(userId);
        coreJobProgress.setUpdatedAt(new Date());
        boolean update = updateById(coreJobProgress);
        if (!update) {
            log.info("修改用户任务进度：{}失败，操作人：{}，完成", id, userId);
            throw new BusinessException(JobErrorEnum.JOB_UPDATE_ERROR);
        }
        log.info("修改用户任务进度：{}成功，操作人：{}，完成", id, userId);
        return coreJobProgress.toCoreJobProgressVo();
    }

    @Override
    public CoreJobProgressVO getJobProgressByUserId(Long userId) {
        return baseMapper.getJobProgressByUserId(userId);
    }
}
