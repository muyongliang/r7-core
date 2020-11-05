package com.r7.core.job.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.job.constant.JobErrorEnum;
import com.r7.core.job.dto.CoreJobDTO;
import com.r7.core.job.dto.CoreJobStatusDTO;
import com.r7.core.job.mapper.CoreJobMapper;
import com.r7.core.job.model.CoreJob;
import com.r7.core.job.service.CoreJobService;
import com.r7.core.job.vo.CoreJobVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 任务实现层
 * @author zs
 */
@Slf4j
@Service
public class CoreJobServiceImpl extends ServiceImpl<CoreJobMapper, CoreJob> implements CoreJobService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoreJobVO saveJob(CoreJobDTO coreJobDto, Long appId, Long userId) {
        log.info("新增任务：{}，操作人{}，开始时间：{}", coreJobDto, userId, new Date());
        CoreJob coreJob = new CoreJob();
        coreJob.toCoreJob(coreJobDto);
        Long id = SnowflakeUtil.getSnowflakeId();
        coreJob.setId(id);
        coreJob.setAppId(appId);
        coreJob.setWinnerNum(2);
        coreJob.setCreatedBy(userId);
        coreJob.setCreatedAt(new Date());
        coreJob.setUpdatedBy(userId);
        coreJob.setUpdatedAt(new Date());
        boolean save = save(coreJob);
        if (!save) {
            throw new BusinessException(JobErrorEnum.JOB_SAVE_ERROR);
        }
        log.info("新增任务：{}成功，操作人{}，结束时间：{}", coreJobDto, userId, new Date());
        return findJobById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoreJobVO updateJobById(Long id, CoreJobDTO coreJobDto, Long userId) {
        log.info("修改任务id：{}，操作人{}，修改内容：{}", id, userId, coreJobDto);
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(JobErrorEnum.JOB_ID_IS_NULL));
        CoreJob coreJob = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(JobErrorEnum.JOB_IS_NOT_EXISTS));
        coreJob.toCoreJob(coreJobDto);
        coreJob.setUpdatedBy(userId);
        coreJob.setUpdatedAt(new Date());
        boolean update = updateById(coreJob);
        if (!update) {
            throw new BusinessException(JobErrorEnum.JOB_UPDATE_ERROR);
        }
        log.info("修改任务id：{}，操作人{}，完成", id, userId);
        return coreJob.toCoreJobVo();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoreJobVO updateJobStatusById(Long id, CoreJobStatusDTO coreJobStatusDto, Long userId) {
        log.info("修改任务id:{}，操作人{}，修改内容：{}", id, userId, coreJobStatusDto);
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(JobErrorEnum.JOB_ID_IS_NULL));
        if (id.toString().length() != 19) {
            throw new BusinessException(JobErrorEnum.JOB_ID_LENGTH_IS_INCORRECT);
        }
        CoreJob coreJob = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(JobErrorEnum.JOB_IS_NOT_EXISTS));
        coreJob.toCoreJobStatusVo(coreJobStatusDto);
        coreJob.setUpdatedBy(userId);
        coreJob.setUpdatedAt(new Date());
        Date offShelf = coreJob.getOffShelf();
        Date onShelf = coreJob.getOnShelf();
        if (offShelf.before(onShelf)) {
            throw new BusinessException(JobErrorEnum.JOB_OFF_SHELF_ERROR);
        }
        boolean update = updateById(coreJob);
        if (!update) {
            throw new BusinessException(JobErrorEnum.JOB_UPDATE_ERROR);
        }
        log.info("修改任务id：{}，操作人{}，任务下架完成", id, userId);
        return coreJob.toCoreJobVo();
    }

    @Override
    public CoreJobVO findJobById(Long id) {
        Option.of(id).getOrElseThrow(() -> new BusinessException(JobErrorEnum.JOB_ID_IS_NULL));
        if (id.toString().length() != 19) {
            throw new BusinessException(JobErrorEnum.JOB_ID_LENGTH_IS_INCORRECT);
        }
        CoreJobVO coreJobVo = new CoreJobVO();
        CoreJob coreJob = baseMapper.selectById(id);
        BeanUtils.copyProperties(coreJob, coreJobVo);
        return coreJobVo;
    }

    @Override
    public Page<CoreJobVO> pageJob(String search, Integer pageNum, Integer pageSize) {
        Page<CoreJobVO> page = new Page<>(pageNum, pageSize);
        return baseMapper.pageJob(search, page);
    }

    @Override
    public Page<CoreJobVO> pageCurrentJob(Long appId, Long platformAppId, Integer pageNum, Integer pageSize) {
        Page<CoreJobVO> page = new Page<>(pageNum, pageSize);
        return baseMapper.pageCurrentJob(appId, platformAppId, page);
    }

}
