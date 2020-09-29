package com.r7.core.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.job.model.CoreJob;
import com.r7.core.job.vo.CoreJobVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 任务Mapper接口
 * @author zs
 */

@Mapper
@Component
public interface CoreJobMapper extends BaseMapper<CoreJob> {


}
