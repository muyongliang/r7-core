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
 * 任务Mapper层
 * @author zs
 */

public interface CoreJobMapper extends BaseMapper<CoreJob> {
    /**
     * 分页查询
     * @param search 条件
     * @param page 分页
     * @return 分页结果
     */
    Page<CoreJobVo> pageJob(@Param("search") String search, @Param("page") Page<CoreJobVo> page);

    /**
     * 当前模块与公共模块的任务分页
     * @param appId 平台id
     * @param platformAppId 公共平台id
     * @param page 分页
     * @return 分页结果
     */
    Page<CoreJobVo> pageCurrentJob(@Param("appId") Long appId, @Param("platformAppId") Long platformAppId, @Param("page") Page<CoreJobVo> page);


}
