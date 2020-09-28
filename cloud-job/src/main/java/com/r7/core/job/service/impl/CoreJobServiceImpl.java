package com.r7.core.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.job.constant.JobErrorEnum;
import com.r7.core.job.dto.CoreJobDto;
import com.r7.core.job.mapper.CoreJobMapper;
import com.r7.core.job.model.CoreJob;
import com.r7.core.job.service.CoreJobService;
import com.r7.core.job.vo.CoreJobVo;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 任务实现层
 * @author zs
 */

@Slf4j
@Service
public class CoreJobServiceImpl extends ServiceImpl<CoreJobMapper, CoreJob> implements CoreJobService {

    @Resource
    private CoreJobMapper coreJobMapper;

    @Override
    public CoreJobVo saveJob(Long appId, CoreJobDto coreJobDto, Long userId) {
        log.info("新增任务：{}，开始时间：{}", coreJobDto, new Date());
        CoreJob coreJob = new CoreJob();
        coreJob.toCoreJob(coreJobDto);
        coreJob.setId(SnowflakeUtil.getSnowflakeId());
        coreJob.setAppId(appId);
        coreJob.setWinnerNum(0);
        coreJob.setCreatedBy(2L);
        coreJob.setCreatedAt(new Date());
        coreJob.setUpdatedBy(2L);
        coreJob.setUpdatedAt(new Date());
        baseMapper.insert(coreJob);
        log.info("新增任务：{}成功，结束时间：{}", coreJobDto, new Date());
        return null;
    }

    @Override
    public boolean removeJobById(Long id, Long userId) {
        log.info("删除任务id：{}", id);
        baseMapper.deleteById(id);
        log.info("删除任务id:{}，完成", id);
        return true;
    }

    @Override
    public CoreJobVo updateJobById(Long id, CoreJobDto coreJobDto, Long userId) {
        log.info("修改任务id：{}，修改内容：{}", id, coreJobDto);
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(JobErrorEnum.JOB_ID_IS_NULL));
        CoreJob coreJob = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(JobErrorEnum.JOB_IS_NOT_EXISTS));
        coreJob.toCoreJob(coreJobDto);
        coreJob.setUpdatedBy(21L);
        coreJob.setUpdatedAt(new Date());
        baseMapper.updateById(coreJob);
        log.info("修改任务id：{}，完成", id);
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
    public Page<CoreJobVo> pageJob(Integer status, String jobName, Integer pageNum, Integer pageSize) {
        QueryWrapper<CoreJob> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (StringUtils.isNotBlank(jobName)) {
            wrapper.eq("job_name", jobName);
        }
        Page<CoreJobVo> page = baseMapper.selectPage(new Page(pageNum, pageSize), wrapper);
        return page;
    }

    @Override
    public Page<CoreJobVo> pageCurrentJob(Long appId, Integer pageNum, Integer pageSize) {
        QueryWrapper<CoreJob> wrapper = new QueryWrapper<>();
        Long platformAppId = 125L;
        Page<CoreJob> pageJobs = coreJobMapper.pageJobs(platformAppId, appId, pageNum, pageSize);
        
//        Page<CoreJobVo> page = baseMapper.selectPage(new Page(pageNum, pageSize), wrapper);
        return null;
    }


}
