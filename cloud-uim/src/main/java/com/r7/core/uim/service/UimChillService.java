package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.dto.UimChillSaveDTO;
import com.r7.core.uim.dto.UimChillSaveListDTO;
import com.r7.core.uim.model.UimChill;
import com.r7.core.uim.vo.UimChillVO;

import java.util.List;

/**
 * @author zs
 * @description: 冻结服务层
 * @date : 2020-10-13
 */
public interface UimChillService extends IService<UimChill> {

    /**
     * 新增冻结用户
     * @param uimChillSaveDto 冻结用户传输实体
     * @param appId       平台ID
     * @param organId     组织ID
     * @param userId      操作人ID
     * @return 返回是否成功
     */
    UimChillVO saveUimChill(UimChillSaveDTO uimChillSaveDto, Long appId, Long organId, Long userId);

    /**
     * 批量冻结用户权限
     * @param uimChillSaveListDTO 冻结传输实体
     * @param appId               平台id
     * @param organId             组织id
     * @param userId              用户id
     * @return 返回是否成功
     */
    Boolean frostUimChill(UimChillSaveListDTO uimChillSaveListDTO, Long appId, Long organId, Long userId);

    /**
     * 解冻全部
     *
     * @param meltingUserId 解冻用户id
     * @param appId   平台id
     * @param organId 组织id
     * @param userId  操作人id
     * @return 返回结果
     */
    Boolean meltingUimChill(Long meltingUserId, Long appId, Long organId, Long userId);

    /**
     * 根据用户id查询冻结信息
     *
     * @param userId 用户id
     * @return 返回结果
     */
    List<UimChillVO> listChillByUserId(Long userId);

}
