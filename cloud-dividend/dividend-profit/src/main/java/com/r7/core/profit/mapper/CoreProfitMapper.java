package com.r7.core.profit.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.profit.model.CoreProfit;
import com.r7.core.profit.vo.CoreProfitVO;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @Description 分润明细mapper层
 * @author wutao
 *
 */
public interface CoreProfitMapper extends BaseMapper<CoreProfit> {
    /**
     * 根据条件分页查询
     * @param userId 分润用户id
     * @param orderId 订单id
     * @param appId 平台id
     * @param type 分润类型
     * @param page 分页
     * @return 返回查询结果
     */
    Page<CoreProfitVO> pageCoreProfit(@Param("userId")Long userId,
                                      @Param("orderId") Long orderId,
                                      @Param("appId") Long appId,
                                      @Param("type") Integer type,
                                      @Param("page") Page<CoreProfitVO> page);

    /**
     * 根据条件分页查询
     * @param wrapper
     * @return
     */
    Page<CoreProfitVO> page(@Param(Constants.WRAPPER) Wrapper<CoreProfit> wrapper);
}