package com.r7.core.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.r7.core.job.model.CoreJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 任务Mapper接口
 * @author zs
 */

@Mapper
@Component
public interface CoreJobMapper extends BaseMapper<CoreJob> {

}
