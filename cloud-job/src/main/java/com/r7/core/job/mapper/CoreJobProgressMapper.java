package com.r7.core.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.r7.core.job.model.CoreJobProgress;
import com.r7.core.job.vo.CoreJobProgressVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author zs
 * @description: 任务进度mapper层
 * @date : 2020-09-28
 */
public interface CoreJobProgressMapper extends BaseMapper<CoreJobProgress> {

    /**
     * 根据用户id查询用户任务进度
     * @param userId 用户id
     * @return 任务进度信息
     */
    CoreJobProgressVo getJobProgressByUserId(@Param("userId") Long userId);
}
