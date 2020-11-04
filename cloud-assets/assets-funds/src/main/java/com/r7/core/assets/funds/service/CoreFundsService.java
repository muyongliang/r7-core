package com.r7.core.assets.funds.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.assets.funds.constant.FundsChannelEnum;
import com.r7.core.assets.funds.constant.FundsStatusEnum;
import com.r7.core.assets.funds.constant.FundsTransactionStatusEnum;
import com.r7.core.assets.funds.dto.CoreFundsDTO;
import com.r7.core.assets.funds.model.CoreFunds;
import com.r7.core.assets.funds.vo.CoreFundsPageVO;
import com.r7.core.assets.funds.vo.CoreFundsVO;

/**
 * @author zs
 * @description: 资金服务层
 * @date : 2020-10-28
 */
public interface CoreFundsService extends IService<CoreFunds> {

    /**
     * 新增资金记录
     *
     * @param coreFundsDto 资金流动记录传输实体
     * @param appId        平台id
     * @param organId      组织id
     * @param userId       用户id
     * @return 返回查询结果
     */
    Boolean saveFunds(CoreFundsDTO coreFundsDto, Long appId, Long organId, Long userId);

    /**
     * 根据id查询资金流动记录详情
     *
     * @param id 资金记录id
     * @return 返回查询结果
     */
    CoreFundsVO getFundsById(Long id);

    /**
     * 根据条件用户id和条件分页查询资金流动情况
     *
     * @param userId            用户id
     * @param status            支付状态
     * @param transactionStatus 交易状态
     * @param channel           支付渠道
     * @param startDate         开始时间
     * @param endDate           结束时间
     * @param pageNum           当前页
     * @param pageSize          总页数
     * @return 返回查询结果
     */
    IPage<CoreFundsPageVO> pageFundsByUserId(Long userId, FundsStatusEnum status, FundsTransactionStatusEnum transactionStatus, FundsChannelEnum channel,
                                             String startDate, String endDate, Integer pageNum, Integer pageSize);

    /**
     * 根据条件分页查询所有用户资金流动情况
     *
     * @param appId             平台id
     * @param status            支付状态
     * @param transactionStatus 交易状态
     * @param channel           支付渠道
     * @param startDate         开始时间
     * @param endDate           结束时间
     * @param pageNum           当前页
     * @param pageSize          总页数
     * @return 返回查询结果
     */
    IPage<CoreFundsPageVO> pageFundsByAppId(Long appId, FundsStatusEnum status, FundsTransactionStatusEnum transactionStatus, FundsChannelEnum channel,
                                            String startDate, String endDate, Integer pageNum, Integer pageSize);

}
