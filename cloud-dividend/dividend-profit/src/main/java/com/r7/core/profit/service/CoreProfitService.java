package com.r7.core.profit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.profit.dto.CoreProfitDTO;
import com.r7.core.profit.model.CoreProfit;
import com.r7.core.profit.vo.CoreProfitVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 * @Description 分润明细service层
 * @author wutao
 *
 */
public interface CoreProfitService extends IService<CoreProfit>{

    /**
     * 添加分润明细
     * @param coreProfitDTO 新增加的分润明细信息
     * @param optionalUserId 操作者
     * @return 返回添加后的信息
     */
    CoreProfitVO saveCoreProfit(CoreProfitDTO coreProfitDTO , Long optionalUserId);

    /**
     * 根据分润明细id查询分润明细详情
     * @param id 分润明细id
     * @param appId 平台id
     * @return 返回查询结果
     */
    CoreProfitVO getCoreProfitById(Long id, Long appId);

    /**
     * 根据条件分页查询
     * @param userId 分润用户id
     * @param orderId 订单id
     * @param appId 平台id
     * @param type 分润类型
     * @param pageNum 当前页
     * @param pageSize 每页显示条数
     * @return 返回查询结果
     */
    Page<CoreProfitVO> pageCoreProfit(Long userId,
                                      Long orderId,
                                      Long appId,
                                      Integer type,
                                      Integer pageNum,
                                      Integer pageSize);


    /**
     * 根据分润明细id修改分润的计算状态并设置发放记录明细id
     * @param id 分润明细id
     * @param appId 平台id
     * @param status 计算状态
     * @param recordIncomeId 发放记录明细id
     * @param optionalUserId 操作人
     * @return 返回修改结果
     */
    CoreProfitVO updateCoreProfitStatusById(Long id, Long appId,
                                            Integer status,
                                            Long recordIncomeId,
                                            Long optionalUserId);

    /**
     * 发放前核算指定用户金额
     * @param userId 用户id
     * @param appId 平台id
     * @param status 计算状态
     * @param recordIncomeId 发放记录明细id
     * @param endTime 核算的截止时间
     * @return 结算结果
     */
    List settlementAmount(Long userId, Long appId,
                          Integer status, Long recordIncomeId,
                          LocalDateTime endTime);

    /**
     * 发放前核算指定用户积分
     * @param userId 用户id
     * @param appId 平台id
     * @param status 计算状态
     * @param recordIncomeId 发放记录明细id
     * @param endTime 核算的截止时间
     * @return 结算结果
     */
    List settlementIntegral(Long userId, Long appId,
                            Integer status, Long recordIncomeId,
                            LocalDateTime endTime);

    /**
     * 根据用户id及计算状态查询分润明细
     * @param userId 用户id
     * @param appId 平台id
     * @param status 计算状态
     * @param endTime 截止时间
     * @return 查询结果
     */
    List<CoreProfit> getCoreProfitByUserId(Long userId, Long appId,
                                           Integer status , LocalDateTime endTime);

    /**
     * 把未计算的截止时间以前的分润明细查询出来
     * @param status 计算状态
     * @param endTime 截止时间
     * @return 查询结果
     */
    List<CoreProfit> getAllCoreProfitByStatus(Integer status, LocalDateTime endTime);


}
