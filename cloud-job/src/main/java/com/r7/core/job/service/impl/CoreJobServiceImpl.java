package com.r7.core.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.job.constant.JobErrorEnum;
import com.r7.core.job.dto.CoreJobDto;
import com.r7.core.job.dto.CoreJobStatusDto;
import com.r7.core.job.mapper.CoreJobMapper;
import com.r7.core.job.model.CoreJob;
import com.r7.core.job.service.CoreJobService;
import com.r7.core.job.vo.CoreJobVo;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 任务实现层
 * @author zs
 */
@Slf4j
@Service
public class CoreJobServiceImpl extends ServiceImpl<CoreJobMapper, CoreJob> implements CoreJobService {

    @Override
    public CoreJobVo saveJob(CoreJobDto coreJobDto, Long appId, Long userId) {
        log.info("新增任务：{}，开始时间：{}", coreJobDto, new Date());
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
        log.info("新增任务：{}成功，结束时间：{}", coreJobDto, new Date());
        return findJobById(id);
    }

    @Override
    public CoreJobVo updateJobById(Long id, CoreJobDto coreJobDto, Long userId) {
        log.info("修改任务id：{}，修改内容：{}", id, coreJobDto);
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
        log.info("修改任务id：{}，完成", id);
        return coreJob.toCoreJobVo();
    }

    @Override
    public CoreJobVo updateJobStatusById(Long id, CoreJobStatusDto coreJobStatusDto, Long userId) {
        log.info("修改任务id:{}，修改内容：{}", id, coreJobStatusDto);
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(JobErrorEnum.JOB_ID_IS_NULL));
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
        log.info("修改任务id：{}，任务下架完成", id);
        return coreJob.toCoreJobVo();
    }

    @Override
    public CoreJobVo findJobById(Long id) {
        CoreJobVo coreJobVo = new CoreJobVo();
        CoreJob coreJob = baseMapper.selectById(id);
        BeanUtils.copyProperties(coreJob, coreJobVo);
        return coreJobVo;
    }

    @Override
    public Page<CoreJobVo> pageJob(String search, Integer pageNum, Integer pageSize) {
        Page<CoreJobVo> page = new Page<>(pageNum, pageSize);
        return baseMapper.pageJob(search, page);
    }

    @Override
    public Page<CoreJobVo> pageCurrentJob(Long appId, Long platformAppId, Integer pageNum, Integer pageSize) {
        Page<CoreJobVo> page = new Page<>(pageNum, pageSize);
        return baseMapper.pageCurrentJob(appId, platformAppId, page);
    }

}
