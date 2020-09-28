package com.r7.core.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.r7.core.job.model.CoreJobProgress;
import com.r7.core.job.vo.CoreJobProgressVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author zs
 * @description:
 * @date : 2020-09-28
 */
@Mapper
@Component
public interface CoreJobProgressMapper extends BaseMapper<CoreJobProgress> {

    CoreJobProgress getJobProgressByUserId(@Param("userId") Long userId);
}
