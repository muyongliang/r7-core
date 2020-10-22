package com.r7.core.trigger.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.trigger.constant.CoreQuartzJobErrorEnum;
import com.r7.core.trigger.dto.CoreQuartzJobInfoDTO;
import com.r7.core.trigger.vo.CoreQuartzJobInfoVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.trigger.mapper.CoreQuartzJobInfoMapper;
import com.r7.core.trigger.model.CoreQuartzJobInfo;
import com.r7.core.trigger.service.CoreQuartzJobInfoService;

import java.time.LocalDateTime;

/**
 * 
 * @Description 定时任务执行情况实现层
 * @author wutao
 * @date 2020/9/28
 */
@Slf4j
@Service
public class CoreQuartzJobInfoServiceImpl extends ServiceImpl<CoreQuartzJobInfoMapper, CoreQuartzJobInfo>
        implements CoreQuartzJobInfoService{

    @Override
    public CoreQuartzJobInfoVO saveCoreQuartzJobInfo(CoreQuartzJobInfoDTO coreQuartzJobInfoDto,
                                                     LocalDateTime startAt , LocalDateTime endAt) {
        log.info("添加定时任务执行情况记录：{}，作业的开始时间：{}，作业的结束时间：{}，开始时间：{}",
                coreQuartzJobInfoDto, startAt,endAt, LocalDateTime.now());
        CoreQuartzJobInfo coreQuartzJobInfo = new CoreQuartzJobInfo();
        coreQuartzJobInfo.toCoreQuartzJobInfoDto(coreQuartzJobInfoDto);
        coreQuartzJobInfo.setId(SnowflakeUtil.getSnowflakeId());


        coreQuartzJobInfo.setStartAt(startAt);
        coreQuartzJobInfo.setEndAt(endAt);
        coreQuartzJobInfo.setCreatedAt(LocalDateTime.now());
        coreQuartzJobInfo.setUpdatedAt(LocalDateTime.now());
       int result = baseMapper.insert(coreQuartzJobInfo);
        if (result != 1) {
            log.info("添加定时任务执行情况记录失败：{}，时间：{}",
                    coreQuartzJobInfoDto,  LocalDateTime.now());
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_OPTIONAL_ERROR);
        }
        log.info("添加定时任务执行情况记录：{}，作业的开始时间：{}，作业的结束时间：{}，结束时间：{}",
                coreQuartzJobInfoDto, startAt,endAt, LocalDateTime.now());
        return coreQuartzJobInfo.toCoreQuartzJobInfoVo();
    }

    @Override
    public Page<CoreQuartzJobInfoVO> pageCoreQuartzJob(Long jobId, Integer pageNum, Integer pageSize) {
        log.info("查询全部定时任务操作记录：查询参数1：{}，参数2：{}，参数3：{},开始时间：{}"
                ,jobId ,pageNum,pageSize,
                LocalDateTime.now());

        //验证id是否为空
        jobId = Option.of(jobId)
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_ID_IS_NULL));

        //执行查询操作
        QueryWrapper<CoreQuartzJobInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("job_id", jobId);
        Page<CoreQuartzJobInfoVO> page = baseMapper.selectPage(new Page(pageNum, pageSize),wrapper);
        log.info("查询全部定时任务操作记录：查询参数1：{}，参数2：{}，参数3：{},结束时间：{}"
                ,jobId ,pageNum,pageSize,
                LocalDateTime.now());
        return page;
    }

    @Override
    public int countCoreQuartzJob(Long id) {
        //定时统计同一个定时任务下所有执行情况
        return baseMapper.selectCount(Wrappers.<CoreQuartzJobInfo>lambdaQuery().eq(CoreQuartzJobInfo::getJobId,id));
    }

    @Override
    public int countCoreQuartzJobFailure(Long id) {
        //定时统计同一个定时任务下的状态为2的执行情况的数量
        return baseMapper.selectCount(Wrappers.<CoreQuartzJobInfo>lambdaQuery()
                .eq(CoreQuartzJobInfo::getJobId,id).eq(CoreQuartzJobInfo::getStatus,2));
    }
}
