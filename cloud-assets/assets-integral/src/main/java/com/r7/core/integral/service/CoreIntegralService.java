package com.r7.core.integral.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.integral.dto.CoreIntegralDTO;
import com.r7.core.integral.model.CoreIntegral;
import com.r7.core.integral.vo.CoreIntegralVO;

import java.time.LocalDateTime;

/**
*
* @Description 当前用户积分service层
* @author wt
*
*/
public interface CoreIntegralService extends IService<CoreIntegral>{


    /**
     * 新增用户积分信息
     * @param coreIntegralDTO 当前用户积分新增信息
     * @param userId 操作者ID
     * @return 返回新增信息
     *
     */
    CoreIntegralVO saveCoreIntegral(CoreIntegralDTO coreIntegralDTO, Long userId);

    /**
     * 增加积分
     * @param businessCode 业务编号
     * @param addInteger 增加的积分
     * @param userId 用户id
     * @param sourceType 积分来源类型
     * @param appId 平台Id
     * @param operationalUserId 操作者
     * @return 当前用户积分信息
     */
    CoreIntegralVO updateCoreIntegralAddTotal(String businessCode,Integer addInteger,Long userId,
                                              Integer sourceType,
                                              Long appId,Long operationalUserId);


    /**
     * 积分减少
     * @param businessCode 业务编号
     * @param reduceInteger 减少的积分数
     * @param userId 用户id
     * @param sourceType 积分来源类型
     * @param appId 平台Id
     * @param operationalUserId 操作者id
     * @return 当前用户积分信息
     */
    CoreIntegralVO updateCoreIntegralReduceTotal(String businessCode,Integer reduceInteger,Long userId,
                                                 Integer sourceType,
                                                 Long appId,Long operationalUserId);


    /**
     * 根据用户ID查询用户当前积分信息
     * @param userId 用户id
     * @return 当前用户总积分
     */
    CoreIntegralVO getCoreIntegralByUserId(Long userId);

    /**
     * 根据用户积分信息ID,查询用户积分信息
     * @param id 当前用户的积分id
     * @return 当前用户的积分信息
     */
    CoreIntegralVO getCoreIntegralById(Long id);

    /**
     * 根据时间分页展示每个用户的当前积分信息
     * @param userId 用户id
     * @param pageNum 当前页
     * @param pageSize 每页显示数
     * @return 当前积分信息
     */
   Page<CoreIntegralVO> pageCoreIntegralPage(
                                            Long userId,
                                             Long pageNum, Long pageSize);


    /**
     * 根据用户id修改用户的总积分
     * @param userId 用户id
     * @param totalInteger 用户新的总积分
     * @param sign 用户积分新的签名
     * @param operationalUserId 操作者id
     * @return 返回修改后的积分信息
     */

    CoreIntegralVO updateCoreIntegralByUserId(Long userId,Integer totalInteger,String sign,Long operationalUserId );






}
