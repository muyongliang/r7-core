package com.r7.core.uim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.uim.model.CorePlatform;
import com.r7.core.uim.vo.CorePlatformVO;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @Description 
 * @author wt
 * 
 */
public interface CorePlatformMapper extends BaseMapper<CorePlatform> {


    /**
     * 分页查询平台信息
     * @param appName 平台名称
     * @param page 分页
     * @return 平台信息
     */
    Page<CorePlatformVO> pagePlatform(@Param("appName") String appName,
                                      @Param("page") Page<CorePlatformVO> page);
}