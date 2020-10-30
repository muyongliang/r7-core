package com.r7.core.integral.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.integral.model.CoreIntegralDetail;
import com.r7.core.integral.vo.CoreIntegralDetailVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author wt
 * @Description 积分详情mapper层
 */
public interface CoreIntegralDetailMapper extends BaseMapper<CoreIntegralDetail> {

    /**
     * 分页展示每个用户的积分详情记录
     *
     * @param businessCode 业务编号
     * @param sourceType   积分来源类型
     * @param appId        平台id
     * @param operateType  操作类型
     * @param page         分页
     * @return 积分详情记录
     */
    Page<CoreIntegralDetailVO> pageCoreIntegralDetailAll(@Param("businessCode") String businessCode,
                                                         @Param("sourceType") Integer sourceType,
                                                         @Param("appId") Long appId,
                                                         @Param("operateType") Integer operateType,
                                                         @Param("page") Page<CoreIntegralDetailVO> page);


    /**
     * 分页展示某一个用户的积分详情记录
     *
     * @param userId      当前用户id
     * @param appId       平台id
     * @param operateType 操作类型
     * @param sourceType  来源类型
     * @param page        分页
     * @return 该用户的积分详情记录
     */
    Page<CoreIntegralDetailVO> pageCoreIntegralDetailByUserId(@Param("userId") Long userId,
                                                              @Param("appId") Long appId,
                                                              @Param("operateType") Integer operateType,
                                                              @Param("sourceType") Integer sourceType,
                                                              @Param("page") Page<CoreIntegralDetailVO> page);
}