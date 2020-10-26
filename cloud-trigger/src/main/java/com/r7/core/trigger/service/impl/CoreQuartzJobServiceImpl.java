package com.r7.core.trigger.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.common.exception.BusinessException;

import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.trigger.constant.CoreQuartzJobErrorEnum;
import com.r7.core.trigger.dto.CoreQuartzJobDTO;
import com.r7.core.trigger.service.QuartzOptionalService;
import com.r7.core.trigger.vo.CoreQuartzJobUpdateVO;
import com.r7.core.trigger.vo.CoreQuartzJobVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.trigger.mapper.CoreQuartzJobMapper;
import com.r7.core.trigger.model.CoreQuartzJob;
import com.r7.core.trigger.service.CoreQuartzJobService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @Description 定时任务实现层
 * @author wutao
 * @date 2020/9/28
 */
@Slf4j
@Service
public class CoreQuartzJobServiceImpl extends ServiceImpl<CoreQuartzJobMapper, CoreQuartzJob>
        implements CoreQuartzJobService{
    @Autowired
    QuartzOptionalService quartzOptionalService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CoreQuartzJobVO saveCoreQuartzJob(CoreQuartzJobDTO coreQuartzJobDto, Long userId) {
        log.info("添加定时任务：{}，操作人：{}，开始时间：{}", coreQuartzJobDto, userId,
                LocalDateTime.now());
        String jobName =  coreQuartzJobDto.getJobName();
        if (Optional.ofNullable(getCoreQuartzJobByJobName(jobName))
                .isPresent()) {
            throw  new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_NAME_IS_NOT_EXISTS);
        }

        String jobGroup = coreQuartzJobDto.getJobGroup();
        if (Optional.ofNullable(getCoreQuartzJobByJobGroup(jobGroup))
                .isPresent()) {
            throw  new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_GROUP_IS_NOT_EXISTS);
        }

        String jobClass = coreQuartzJobDto.getJobClass();
        if (Optional.ofNullable(getCoreQuartzJobByJobClass(jobClass))
                .isPresent()) {
            throw  new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_CLASS_IS_NOT_EXISTS);
        }

        CoreQuartzJob coreQuartzJob = new CoreQuartzJob();
        coreQuartzJob.toCoreQuartzJobDto(coreQuartzJobDto);
        //雪花算法生成id
        coreQuartzJob.setId(SnowflakeUtil.getSnowflakeId());
        //定时任务状态默认为1
        coreQuartzJob.setStatus(1);
        coreQuartzJob.setCreatedBy(userId);
        coreQuartzJob.setCreatedAt(LocalDateTime.now());
        coreQuartzJob.setUpdatedBy(userId);
        coreQuartzJob.setUpdatedAt(LocalDateTime.now());
       int result =  baseMapper.insert(coreQuartzJob);
        if (result != 1) {
            log.info("添加定时任务失败：{}，操作人：{}，时间：{}", coreQuartzJobDto, userId,
                    LocalDateTime.now());
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_SAVE_ERROR);
        }
        log.info("添加定时任务成功：{}，操作人：{}，结束时间：{}", coreQuartzJobDto, userId,
                LocalDateTime.now());
        return coreQuartzJob.toCoreQuartzJobVo();
    }

    @Override
    public CoreQuartzJobVO findCoreQuartzJobById(Long id) {
        log.info("根据id查询定时任务：{}，开始时间：{}", id,
                LocalDateTime.now());
        //验证id是否为空
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_ID_IS_NULL));

        //根据id查询，没有时返回一个空的实体
        CoreQuartzJob coreQuartzJob = Option.of(baseMapper.selectById(id))
                .getOrElse(new CoreQuartzJob());
        log.info("根据id查询定时任务成功：{}，结束时间：{}", id,
                LocalDateTime.now());
        return coreQuartzJob.toCoreQuartzJobVo();
    }

    @Override
    public CoreQuartzJob getCoreQuartzJobByJobName(String jobName) {
        jobName = Option.of(jobName)
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_NAME_IS_NULL));
        return baseMapper.selectOne(Wrappers.<CoreQuartzJob>lambdaQuery().eq(CoreQuartzJob::getJobName, jobName));    }

    @Override
    public CoreQuartzJob getCoreQuartzJobByJobGroup(String jobGroup) {
        jobGroup = Option.of(jobGroup)
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_GROUP_IS_NULL));
        return baseMapper.selectOne(Wrappers.<CoreQuartzJob>lambdaQuery().eq(CoreQuartzJob::getJobGroup, jobGroup));
    }

    @Override
    public CoreQuartzJob getCoreQuartzJobByJobClass(String jobClass) {
        jobClass = Option.of(jobClass)
                .getOrElseThrow(() ->
                        new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_CLASS_IS_NULL));
        return baseMapper.selectOne(Wrappers.<CoreQuartzJob>lambdaQuery().eq(CoreQuartzJob::getJobClass, jobClass));
    }

    @Override
    public Page<CoreQuartzJobVO> pageCoreQuartzJob(Integer status, String jobName,
                                                   String jobGroup, String jobClass,
                                                   Integer pageNum, Integer pageSize) {
        log.info("动态查询定时任务查询参数1：{}，参数2：{}，参数3：{}，参数4：{}，参数5：{}，参数6：{}，开始时间：{}"
                ,status ,jobName,jobGroup,jobClass,pageNum,pageSize,
                LocalDateTime.now());
        QueryWrapper<CoreQuartzJob> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("status", status);
        }
        if (StringUtils.isNotBlank(jobName)) {
            wrapper.eq("job_name", jobName);
        }
        if (StringUtils.isNotBlank(jobGroup)) {
            wrapper.eq("job_group", jobGroup);
        }
        if (StringUtils.isNotBlank(jobClass)) {
            wrapper.eq("job_class", jobClass);
        }
        Page<CoreQuartzJobVO> page = baseMapper.selectPage(new Page(pageNum, pageSize), wrapper);
        log.info("动态查询定时任务查询参数1：{}，参数2：{}，参数3：{}，参数4：{}，参数5：{}，参数6：{}，结束时间：{}"
                ,status ,jobName,jobGroup,jobClass,pageNum,pageSize,
                LocalDateTime.now());
        return page;
    }
    @Transactional(rollbackFor=Exception.class)
    @Override
    public CoreQuartzJobVO updateCoreQuartzJobRuleExpressionById(Long id,
                                                                 String ruleExpression ,
                                                                 Long userId) {
        log.info("定时任务ID：{}，修改内容：{}，操作人：{}， 开始时间：{}",
                id,ruleExpression , userId,LocalDateTime.now());

        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_ID_IS_NULL));
        CoreQuartzJob job = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS));

        //先判断任务的状态，如果是待启动则可以直接修改
        if (job.getStatus() == 1) {
            LambdaUpdateWrapper<CoreQuartzJob> updateWrapper = Wrappers.<CoreQuartzJob>lambdaUpdate()
                    .set(CoreQuartzJob::getRuleExpression, ruleExpression)
                    .set(CoreQuartzJob::getUpdatedBy, userId)
                    .set(CoreQuartzJob::getUpdatedAt, LocalDateTime.now())
                    .eq(CoreQuartzJob::getId, id);
            boolean updatRuleExpression =  update(updateWrapper);
            if (!updatRuleExpression ) {
                throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_UPDATE_RULE_EXPRESSION_ERROR);
            }
        }else {
            //1、先把定时任务的暂停
            quartzOptionalService.pause(id, userId);
            //2、修改执行规则
            LambdaUpdateWrapper<CoreQuartzJob> updateWrapper = Wrappers.<CoreQuartzJob>lambdaUpdate()
                    .set(CoreQuartzJob::getRuleExpression, ruleExpression)
                    .set(CoreQuartzJob::getUpdatedBy, userId)
                    .set(CoreQuartzJob::getUpdatedAt, LocalDateTime.now())
                    .eq(CoreQuartzJob::getId, id);
            boolean updatRuleExpression =  update(updateWrapper);
            if (!updatRuleExpression ) {
                throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_UPDATE_RULE_EXPRESSION_ERROR);
            }
            //3、移除定时任务
            quartzOptionalService.removeCoreQuartzJob(id, userId);
            //4、把自己设计的表的定时任务状态修改为待启动
            updateStatusById(id, 1, userId);
            //5、初始化启动定时任务
            quartzOptionalService.startCoreQuartzJob(id, userId);
        }

        CoreQuartzJob coreQuartzJob = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS));

        log.info("定时任务ID：{}，修改内容：{}，操作人：{}， 结束时间：{}",
                id,ruleExpression , userId,LocalDateTime.now());
        return coreQuartzJob.toCoreQuartzJobVo();
    }
    @Transactional(rollbackFor=Exception.class)
    @Override
    public CoreQuartzJobVO updateStatusById(Long id, Integer status, Long userId) {
        log.info("定时任务ID：{}，修改内容：{}，操作人：{}， 开始时间：{}",
                id,status , userId,LocalDateTime.now());
        //检查id是否为空以及定时任务是否存在
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_ID_IS_NULL));
        CoreQuartzJob coreQuartzJob = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS));

        //修改定时任务的状态
        LambdaUpdateWrapper<CoreQuartzJob> updateWrapper = Wrappers.<CoreQuartzJob>lambdaUpdate()
                .set(CoreQuartzJob::getStatus, status)
                .set(CoreQuartzJob::getUpdatedBy, userId)
                .set(CoreQuartzJob::getUpdatedAt, LocalDateTime.now())
                .eq(CoreQuartzJob::getId, id);
        boolean updateStatus =  update(updateWrapper);
        //判断修改是否成功
        if (!updateStatus ) {
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_STATUS_ERROR);
        }
        CoreQuartzJob job = baseMapper.selectById(id);

        log.info("定时任务ID：{}，修改内容：{}，操作人：{}， 结束时间：{}",
                id,status , userId,LocalDateTime.now());
        return job.toCoreQuartzJobVo();
    }
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int updateAllCoreQuartzJobStatus(Integer status, Long userId) {

        log.info("修改内容：{}，操作人：{}， 开始时间：{}", status , userId,LocalDateTime.now());
        //1、先把所有定时任务查询出来2、修改定时任务的状态
        List<CoreQuartzJob> list = findAllCoreQuartzJobByStatus();
        list.forEach(coreQuartzJob -> updateStatusById(coreQuartzJob.getId(),status,userId));
        log.info("修改内容：{}，操作人：{}， 结束时间：{}", status , userId,LocalDateTime.now());

        return 1;
    }
    @Transactional(rollbackFor=Exception.class)
    @Override
    public CoreQuartzJobVO updateCoreQuartzJobStartAtById(LocalDateTime localDateTime, Long id, Long userId) {
        log.info("定时任务id：{}，修改内容：{},操作人：{}， 开始时间：{}",id, localDateTime , userId,LocalDateTime.now());
        //修改定时任务的时间
        LambdaUpdateWrapper<CoreQuartzJob> updateWrapper = Wrappers.<CoreQuartzJob>lambdaUpdate()
                .set(CoreQuartzJob::getStartAt,localDateTime)
                .set(CoreQuartzJob::getUpdatedBy, userId)
                .set(CoreQuartzJob::getUpdatedAt, LocalDateTime.now())
                .eq(CoreQuartzJob::getId, id);
        boolean   updateStartAt =  update(updateWrapper);
        if (!updateStartAt ) {
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_UPDATE_START_AT_ERROR);
        }
        CoreQuartzJob coreQuartzJob =  Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS));
        log.info("定时任务id：{}，修改内容：{},操作人：{}， 结束时间：{}",id, localDateTime , userId,LocalDateTime.now());
        return coreQuartzJob.toCoreQuartzJobVo();
    }
    @Transactional(rollbackFor=Exception.class)
    @Override
    public CoreQuartzJobVO updateCoreQuartzJobRegainAtById(LocalDateTime localDateTime, Long id, Long userId) {
        //修改定时任务的恢复时间
        log.info("定时任务id：{}，修改内容：{},操作人：{}， 开始时间：{}",id, localDateTime , userId,LocalDateTime.now());
        LambdaUpdateWrapper<CoreQuartzJob> updateWrapper = Wrappers.<CoreQuartzJob>lambdaUpdate()
                .set(CoreQuartzJob::getRegainAt,localDateTime)
                .set(CoreQuartzJob::getUpdatedBy, userId)
                .set(CoreQuartzJob::getUpdatedAt, LocalDateTime.now())
                .eq(CoreQuartzJob::getId, id);
        boolean updateRegainAt =   update(updateWrapper);
        if (!updateRegainAt ) {
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_UPDATE_REGAIN_AT_ERROR);
        }
        CoreQuartzJob coreQuartzJob =  Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS));

        log.info("定时任务id：{}，修改内容：{},操作人：{}， 结束时间：{}",id, localDateTime , userId,LocalDateTime.now());
        return coreQuartzJob.toCoreQuartzJobVo();
    }

    @Override
    public List<CoreQuartzJob> findAllCoreQuartzJob(Long userId) {
        log.info("查询内容：{}，操作人：{}， 开始时间：{}","查询全部定时任务",userId,LocalDateTime.now());
        List<CoreQuartzJob> list = baseMapper.selectList(null);
        log.info("查询内容：{}，操作人：{}， 结束时间：{}","查询全部定时任务",userId,LocalDateTime.now());
        return list;
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public CoreQuartzJobVO updateCoreQuartzJobDescriptionById(Long id, String description, Long userId) {
        log.info("定时任务id：{}，修改内容：{},操作人：{}， 开始时间：{}",id, description , userId,LocalDateTime.now());


        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_ID_IS_NULL));
        CoreQuartzJob job = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS));

        LambdaUpdateWrapper<CoreQuartzJob> updateWrapper = Wrappers.<CoreQuartzJob>lambdaUpdate()
                .set(CoreQuartzJob::getDescription, description)
                .set(CoreQuartzJob::getUpdatedBy, userId)
                .set(CoreQuartzJob::getUpdatedAt, LocalDateTime.now())
                .eq(CoreQuartzJob::getId, id);
        boolean updatebDescription =  update(updateWrapper);
        if (!updatebDescription ) {
            throw  new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_UPDATE_DESCRIPTION_ERROR);
        }
        CoreQuartzJob coreQuartzJob =  Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS));

        log.info("定时任务id：{}，修改内容：{},操作人：{}， 结束时间：{}",id, description , userId,LocalDateTime.now());
        return coreQuartzJob.toCoreQuartzJobVo();
    }


    @Transactional(rollbackFor=Exception.class)
    @Override
    public CoreQuartzJobUpdateVO updateCoreQuartzJobDescriptionAndRuleExpressionById(Long id,
                                                                                     String description,
                                                                                     String ruleExpression,
                                                                                     Long userId) {


        log.info("定时任务id：{}，修改描述：{},修改执行规则：{},操作人：{}， 开始时间：{}",id,
                description,ruleExpression ,
                userId,LocalDateTime.now());

        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_ID_IS_NULL));
        CoreQuartzJob job = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS));

        //修改定时任务的执行规则
        if (StringUtils.isNotBlank(ruleExpression)) {
            updateCoreQuartzJobRuleExpressionById(id, ruleExpression, userId);
        }
        if (StringUtils.isNotBlank(description)) {
            //修改定时任务的描述
            updateCoreQuartzJobDescriptionById(id, description, userId);
        }


        CoreQuartzJob coreQuartzJob = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS));

        log.info("定时任务id：{}，修改描述：{},修改执行规则：{},操作人：{}， 结束时间：{}",id,
                description,ruleExpression ,
                userId,LocalDateTime.now());
        return coreQuartzJob.toCoreQuartzJobUpdateVo();
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public CoreQuartzJobVO updateCoreQuartzJobCountAndCountFailure(Long id, int count, int countFailure) {

        log.info("定时任务id：{}，修改总执行次数：{},修改执行失败次数：{}， 开始时间：{}",id,
                count,countFailure ,LocalDateTime.now());
        LambdaUpdateWrapper<CoreQuartzJob> updateWrapper = Wrappers.<CoreQuartzJob>lambdaUpdate()
                .set(CoreQuartzJob::getCount,count)
                .set(CoreQuartzJob::getErrorsNum,countFailure)
                .set(CoreQuartzJob::getUpdatedAt, LocalDateTime.now())
                .eq(CoreQuartzJob::getId, id);
        boolean   updateCountAndCountFailure =  update(updateWrapper);
        if (!updateCountAndCountFailure) {
            throw new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_JOB_COUNT_COUNT_FAILURE_ERROR);
        }

        CoreQuartzJob  coreQuartzJob = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(CoreQuartzJobErrorEnum.CORE_QUARTZ_JOB_IS_NOT_EXISTS));

        log.info("定时任务id：{}，修改总执行次数：{},修改执行失败次数：{}， 结束时间：{}",id,
                count,countFailure ,LocalDateTime.now());

        return coreQuartzJob.toCoreQuartzJobVo();
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public int updateCoreQuartzJobAllRegainAt(LocalDateTime localDateTime, Long userId) {
        log.info("修改所有定时任务的恢复时间：{},操作人：{}， 开始时间：{}",localDateTime,
                userId,LocalDateTime.now());

        //1、先把所有定时任务查询出来2、修改定时任务的恢复时间
//        List<CoreQuartzJob> list =   findAllCoreQuartzJob( userId);
        List<CoreQuartzJob> list = findAllCoreQuartzJobByStatus();
        list.forEach(coreQuartzJob -> updateCoreQuartzJobRegainAtById(localDateTime,
                coreQuartzJob.getId(),userId));

        log.info("修改所有定时任务的恢复时间：{},操作人：{}， 结束时间：{}",localDateTime,
                userId,LocalDateTime.now());
        return 1;
    }

    @Override
    public List<CoreQuartzJob> findAllCoreQuartzJobByStatus() {
        log.info("查询内容：{}， 开始时间：{}","查询全部定时任务",LocalDateTime.now());
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.<CoreQuartzJob>lambdaQuery()
                .ge(CoreQuartzJob::getStatus,2);
        List<CoreQuartzJob> list = baseMapper.selectList(lambdaQueryWrapper);
        log.info("查询内容：{}， 结束时间：{}","查询全部定时任务",LocalDateTime.now());
        return list;
    }
}
