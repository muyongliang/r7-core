package com.r7.core.assets.funds.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.assets.funds.model.CoreFunds;
import com.r7.core.assets.funds.vo.CoreFundsPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author zs
 * @description: 核心资金mapper层
 * @date : 2020-10-28
 */
public interface CoreFundsMapper extends BaseMapper<CoreFunds> {
    /**
     * 根据用户id查询用户资金流动情况
     *
     * @param userId            用户id
     * @param status            支付状态
     * @param transactionStatus 交易状态
     * @param channel           支付渠道
     * @param startDate         开始时间
     * @param endDate           结束时间
     * @param page              分页
     * @return 返回查询结果
     */
    IPage<CoreFundsPageVO> pageFundsByUserId(@Param("userId") Long userId, @Param("status") Integer status, @Param("transactionStatus") Integer transactionStatus, @Param("channel") Integer channel, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("page") Page<CoreFundsPageVO> page);

    /**
     * 根据平台id和条件查询全部用户资金流动情况
     *
     * @param appId             平台id
     * @param status            支付状态
     * @param transactionStatus 交易状态
     * @param channel           支付渠道
     * @param startDate         开始时间
     * @param endDate           结束时间
     * @param page              分页
     * @return 返回查询结果
     */
    IPage<CoreFundsPageVO> pageFundsByAppId(@Param("appId") Long appId, @Param("status") Integer status, @Param("transactionStatus") Integer transactionStatus, @Param("channel") Integer channel, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("page") Page<CoreFundsPageVO> page);


}
