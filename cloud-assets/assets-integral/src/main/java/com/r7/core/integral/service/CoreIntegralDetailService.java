package com.r7.core.integral.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.integral.constant.OperateTypeEnum;
import com.r7.core.integral.constant.SourceTypeEnum;
import com.r7.core.integral.dto.CoreIntegralDetailDTO;
import com.r7.core.integral.model.CoreIntegralDetail;
import com.r7.core.integral.vo.CoreIntegralDetailVO;

/**
 * @author wt
 * @Description 积分详情service层
 */
public interface CoreIntegralDetailService extends IService<CoreIntegralDetail> {

    /**
     * 增加积分详情记录
     *
     * @param coreIntegralDetailDTO 增加的积分详情信息
     * @param appId                 平台ID
     * @param userId                操作者ID
     * @return 返回增加的信息
     */
    CoreIntegralDetailVO saveCoreIntegralDetail(CoreIntegralDetailDTO coreIntegralDetailDTO,
                                                Long appId, Long userId);

    /**
     * 根据积分详情id查询积分详情记录
     *
     * @param id    积分详情id
     * @param appId 平台id
     * @return 返回积分详情信息
     */
    CoreIntegralDetailVO getCoreIntegralDetailById(Long id, Long appId);

    /**
     * 分页展示每个用户的积分详情记录
     *
     * @param businessCode 业务编号
     * @param sourceType   积分来源类型
     * @param appId        平台id
     * @param operateType  操作类型
     * @param pageNum      当前页
     * @param pageSize     每页显示数
     * @return 积分详情记录
     */
    Page<CoreIntegralDetailVO> pageCoreIntegralDetailAll(String businessCode,
                                                         SourceTypeEnum sourceType,
                                                         Long appId,
                                                         OperateTypeEnum operateType,
                                                         Long pageNum, Long pageSize);

    /**
     * 分页展示某一个用户的积分详情记录
     *
     * @param userId      当前用户id
     * @param appId       平台id
     * @param operateType 操作类型
     * @param sourceType  来源类型
     * @param pageNum     当前页
     * @param pageSize    每页显示数
     * @return 该用户的积分详情记录
     */
    Page<CoreIntegralDetailVO> pageCoreIntegralDetailByUserId(Long userId, Long appId,
                                                              OperateTypeEnum operateType,
                                                              SourceTypeEnum sourceType, Long pageNum,
                                                              Long pageSize);

}
