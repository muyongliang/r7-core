package com.r7.core.profit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.profit.model.CoreRecordIncome;
import com.r7.core.profit.vo.CoreProfitVO;
import com.r7.core.profit.vo.CoreRecordIncomeVO;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @Description 发放记录明细mapper层
 * @author wutao
 *
 */
public interface CoreRecordIncomeMapper extends BaseMapper<CoreRecordIncome> {


    /**
     * 根据条件分页查询
     * @param status 发放的状态
     * @param userId 用户id
     * @param appId 平台id
     * @param page 分页
     * @return 返回分页查询结果
     */
    Page<CoreRecordIncomeVO> pageCoreRecordIncome( @Param("status")Integer status ,
                                                   @Param("userId")Long userId,
                                                   @Param("appId")Long appId,
                                                   @Param("page") Page<CoreRecordIncomeVO> page);

    /**
     * 根据用户id分页查询发放状态为2的发放积分记录明细
     * @param userId 用户id
     * @param status 发放状态
     * @param page 分页
     * @return 返回查询结果
     */
    Page<CoreRecordIncomeVO> pageCoreRecordIncomeIntegralByUserId(
            @Param("userId")Long userId,
            @Param("status")Integer status,
            @Param("page") Page<CoreRecordIncomeVO> page
    );

    /**
     * 根据用户id分页查询发放状态为2的发放金额记录明细
     * @param userId 用户id
     * @param status 发放状态
     * @param page 分页
     * @return 返回查询结果
     */
    Page<CoreRecordIncomeVO> pageCoreRecordIncomeAmountByUserId(
            @Param("userId")Long userId,
            @Param("status")Integer status,
            @Param("page") Page<CoreRecordIncomeVO> page
    );

}