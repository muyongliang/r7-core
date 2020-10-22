package com.r7.core.profit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.profit.dto.CoreRecordIncomeDTO;
import com.r7.core.profit.model.CoreRecordIncome;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.profit.vo.CoreRecordIncomeVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 * @Description 发放明细service层
 * @author wutao
 *
 */
public interface CoreRecordIncomeService extends IService<CoreRecordIncome>{
    /**
     * 添加发放记录明细
     * @param id 发放记录id
     * @param coreRecordIncomeDTO 新添加的发放记录明细
     * @param optionalUserId 操作者
     * @return 返回添加后的信息
     */
    CoreRecordIncomeVO saveCoreRecordIncome(Long id, CoreRecordIncomeDTO coreRecordIncomeDTO, Long optionalUserId);

    /**
     * 根据发放明细id查询发放详细信息
     * @param id 发放明细的id
     * @param appId 平台id
     * @return 返回查询结果
     */
    CoreRecordIncomeVO getCoreRecordIncomeById(Long id, Long appId);

    /**
     * 根据条件分页查询发放记录明细
     * @param status 发放状态
     * @param userId 用户id
     * @param appId 平台id
     * @param pageNum 当前页
     * @param pageSize 每页显示数
     * @return 返回分页查询结果
     */
    Page<CoreRecordIncomeVO> pageCoreRecordIncome(Integer status ,
                                                  Long userId,
                                                  Long appId,
                                                  Integer pageNum,
                                                  Integer pageSize);


    /**
     * 根据用户id查询该用户发放的总分润金额
     * @param userId 用户id
     * @return 总的分润金额
     */
    int countTotalAmountByUserId(Long userId);

    /**
     * 根据用户id查询该用户发放的总分润积分
     * @param userId 用户id
     * @return 总的分润积分
     */
    int countTotalIntegralByUserId(Long userId);

    /**
     * 根据用户id查询发放记录
     * @param userId 用户id
     * @param status 发放状态
     * @return 返回查询结果
     */
    List<CoreRecordIncome> getCoreRecordIncomeByUserId(Long userId,
                                                       Integer status);


    /**
     * 根据平台id查询发放记录
     * @param appId 用户id
     * @param status 发放状态
     * @return 返回查询结果
     */
    List<CoreRecordIncome> getCoreRecordIncomeByAppId(Long appId,
                                                       Integer status);

    /**
     * 根据用户id分页查询发放状态为2的发放金额记录明细
     * @param userId 用户id
     * @param pageNum 当前页
     * @param pageSize 每页显示数
     * @return 查询结果
     */
    Page<CoreRecordIncomeVO> getCoreRecordIncomeAmountByUserId(Long userId,
                                                         Integer pageNum,
                                                         Integer pageSize);


    /**
     * 根据用户id分页查询发放状态为2的发放积分记录明细
     * @param userId 用户id
     * @param pageNum 当前页
     * @param pageSize 每页显示数
     * @return 查询结果
     */
    Page<CoreRecordIncomeVO> getCoreRecordIncomeIntegralByUserId(Long userId,
                                                               Integer pageNum,
                                                               Integer pageSize);

    /**
     * 根据发放记录id修改发放的状态
     * @param id 发放记录id
     * @param appId 平台id
     * @param status 发放状态
     * @param distributionAt 发放时间
     * @param description 发放描述
     * @param incomeDate 日期 yyyyMMdd
     * @param optionalUserId 操作者
     * @return 返回修改结果
     */
    CoreRecordIncomeVO updateCoreRecordIncomeStatusById(Long id,
                                                        Long appId,
                                                        Integer status,
                                                        LocalDateTime distributionAt,
                                                        String description,
                                                        Integer incomeDate,
                                                        Long optionalUserId);

    /**
     * 根据发放状态查询发放记录明细
     * @param status 发放装填状态
     * @return 返回查询结果
     */
    List<CoreRecordIncome> getAllCoreRecordIncomeByStatus(Integer status);

    /**
     * 根据平台id统计该平台发放了总的金额
     * @param appId 平台id
     * @return 统计结果
     */
    int countTotalAmountByAppId(Long appId);

    /**
     * 根据平台id统计该平台发放了总的积分
     * @param appId 平台id
     * @return 统计结果
     */
    int countTotalIntegralByAppId(Long appId);
}
