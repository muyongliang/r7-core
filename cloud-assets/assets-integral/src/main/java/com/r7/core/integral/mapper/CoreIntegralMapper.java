package com.r7.core.integral.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.integral.model.CoreIntegral;
import com.r7.core.integral.vo.CoreIntegralVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author wt
 * @Description 当前用户积分mapper层
 */
public interface CoreIntegralMapper extends BaseMapper<CoreIntegral> {


    /**
     * 根据时间分页展示每个用户的当前积分信息
     *
     * @param userId 用户id
     * @param page   分页
     * @return 当前积分信息
     */
    Page<CoreIntegralVO> pageCoreIntegralPage(
            @Param("userId") Long userId,
            @Param("page") Page<CoreIntegral> page);
}