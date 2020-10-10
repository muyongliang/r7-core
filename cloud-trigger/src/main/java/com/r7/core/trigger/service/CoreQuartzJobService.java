package com.r7.core.trigger.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.trigger.dto.CoreQuartzJobDTO;
import com.r7.core.trigger.model.CoreQuartzJob;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.trigger.vo.CoreQuartzJobUpdateVO;
import com.r7.core.trigger.vo.CoreQuartzJobVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 * @Description 定时任务
 * @author wutao
 * @date 2020/9/28
 */
public interface CoreQuartzJobService extends IService<CoreQuartzJob>{

    /**
     * 添加定时任务
     * @param coreQuartzJobDto 定时任务信息
     * @param userId 操作者id
     * @return 返回添加结果
     */
    CoreQuartzJobVO saveCoreQuartzJob(CoreQuartzJobDTO coreQuartzJobDto, Long userId);

    /**
     * 根据定时任务id查询
     * @param id 定时任务id
     * @return 返回查询结果
     */
    CoreQuartzJobVO findCoreQuartzJobById(Long id);

    /**
     * 验证任务名称是否存在
     * @param jobName 定时任务的名称
     * @return 验证结果
     */
    CoreQuartzJob getCoreQuartzJobByJobName(String jobName);

    /**
     * 验证任务分组名是否存在
     * @param jobGroup 定时任务分组的名称
     * @return 验证结果
     */
    CoreQuartzJob getCoreQuartzJobByJobGroup(String jobGroup);

    /**
     * 验证任务路径存在
     * @param jobClass 定时任务的路径
     * @return 验证结果
     */
    CoreQuartzJob getCoreQuartzJobByJobClass(String jobClass);

    /**
     * 根据动态条件分页查询
     * @param status 定时任务状态
     * @param jobName 定时任务名
     * @param jobGroup 定时任务分组
     * @param jobClass 定时任务路径
     * @param pageNum 当前页
     * @param pageSize 每页显示数
     * @return 返回查询结果
     */
    Page<CoreQuartzJobVO> pageCoreQuartzJob(Integer status, String jobName,
                                            String jobGroup, String jobClass,
                                            Integer pageNum, Integer pageSize);

    /**
     * 根据定时任务id修改执行的规则
     * @param id 定时任务id
     * @param ruleExpression 定时任务执行的规则表达式
     * @param userId 操作者id
     * @return 修改结果
     */
    CoreQuartzJobVO updateCoreQuartzJobRuleExpressionById(Long id,
                                                          String ruleExpression,
                                                          Long userId);


    /**
     * 查询所有定时任务
     * @param userId 操作者
     * @return 返回查询结果
     */
    List<CoreQuartzJob> findAllCoreQuartzJob(Long userId );

    /**
     * 根据定时任务id修改定时任务的状态
     * @param id 定时任务id
     * @param status 定时任务的状态
     * @param userId 操作者
     * @return 返回修改结果
     */
    CoreQuartzJobVO updateStatusById(Long id, Integer status, Long userId);

    /**
     * 修改所有定时任务的状态
     * @param status 定时任务的状态
     * @param userId 操作者
     * @return 返回修改结果
     */
    int updateAllCoreQuartzJobStatus(Integer status, Long userId);

    /**
     * 修改定时任务的启动时间
     * @param localDateTime 操作时间
     * @param id 定时任务id
     * @param userId 操作者
     * @return 返回修改结果
     */
    CoreQuartzJobVO updateCoreQuartzJobStartAtById(LocalDateTime localDateTime, Long id, Long userId);

    /**
     * 修改定时任务的恢复时间
     * @param localDateTime 恢复的时间
     * @param id 定时任务的 id
     * @param userId 操作者
     * @return 返回修改结果
     */
    CoreQuartzJobVO updateCoreQuartzJobRegainAtById(LocalDateTime localDateTime, Long id, Long userId);

    /**
     * 根据定时任务的id修改定时任务的描述
     * @param id 定时任务的id
     * @param description 描述
     * @param userId 操作者
     * @return 返回修改结果
     */
    CoreQuartzJobVO updateCoreQuartzJobDescriptionById(Long id, String description, Long userId);

    /**
     * 根据id修改定时任务的描述和执行规则
     * @param id 定时任务的id
     * @param description 描述
     * @param ruleExpression 执行规则
     * @param userId 操作者
     * @return 修改结果
     */
    CoreQuartzJobUpdateVO updateCoreQuartzJobDescriptionAndRuleExpressionById(Long id,
                                                                              String description,
                                                                              String ruleExpression,
                                                                              Long userId);


    /**
     *  修改定时任务的执行次数和执行失败的次数
     * @param id 定时任务的id
     * @param count 执行次数
     * @param countFailure 失败次数
     * @return 返回修改结果
     */
    CoreQuartzJobVO updateCoreQuartzJobCountAndCountFailure(Long id,
                                                            int count,
                                                            int countFailure);

    /**
     * 修改所有定时任务的恢复时间
     *
     * @param localDateTime 恢复时间
     * @param userId 操作者
     * @return 返回修改结果
     */
    int updateCoreQuartzJobAllRegainAt(LocalDateTime localDateTime, Long userId);


    /**
     * 查询初始启动过的所有定时任务
     *
     * @return 返回查询结果
     */
    List<CoreQuartzJob> findAllCoreQuartzJobByStatus();
}
