package com.r7.core.assets.wallet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.assets.wallet.model.CoreWalletBill;
import com.r7.core.assets.wallet.vo.CoreWalletBillPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author zs
 * @description: 钱包账单mapper层
 * @date : 2020-10-26
 */
public interface CoreWalletBillMapper extends BaseMapper<CoreWalletBill> {
    /**
     * 根据用户id和条件分页展示用户钱包账单信息
     *
     * @param userId    用户id
     * @param type      交易类型
     * @param source    交易来源
     * @param status    交易状态
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param page      分页
     * @return 返回查询结果
     */
    IPage<CoreWalletBillPageVO> pageWalletBillByUserId(@Param("userId") Long userId, @Param("type") Integer type, @Param("source") String source, @Param("status") Integer status, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("page") Page<CoreWalletBillPageVO> page);

    /**
     * 根据条件分页查询平台所有用户钱包账单信息
     *
     * @param appId     平台id
     * @param type      交易类型
     * @param source    交易来源
     * @param status    交易状态
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param page      分页
     * @return 返回查询结果
     */
    IPage<CoreWalletBillPageVO> pageWalletBillByAppId(@Param("appId") Long appId, @Param("type") Integer type, @Param("source") String source, @Param("status") Integer status, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("page") Page<CoreWalletBillPageVO> page);

    /**
     * 查询一段时间平台中用户消费总额
     *
     * @param appId     平台id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 返回查询结果
     */
    Integer getTotalConsumptionBalance(@Param("appId") Long appId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
