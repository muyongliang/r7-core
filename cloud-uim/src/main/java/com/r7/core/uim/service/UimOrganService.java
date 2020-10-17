package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.dto.UimOrganSaveDTO;
import com.r7.core.uim.dto.UimOrganUpdateDTO;
import com.r7.core.uim.model.UimOrgan;
import com.r7.core.uim.vo.UimOrganNodeVO;
import com.r7.core.uim.vo.UimOrganVO;

import java.util.List;

/**
 * 组织服务层
 *
 * @author zhongpingli
 */
public interface UimOrganService extends IService<UimOrgan> {


    /**
     * 根据父组织ID添加组织
     *
     * @param pId             父ID
     * @param uimOrganSaveDTO 新增组织信息
     * @param userId          操作用户ID
     * @param appId           平台ID
     * @return 返回新增后信息
     */
    UimOrganVO saveUimOrganByPid(Long pId, UimOrganSaveDTO uimOrganSaveDTO, Long userId, Long appId);


    /**
     * 根据ID删除组织
     *
     * @param id     删除组织ID
     * @param userId 操作用户id
     * @param appId  平台ID
     * @return 返回是否删除
     */
    Boolean removeUimOrganById(Long id, Long userId, Long appId);

    /**
     * 批量删除组织
     *
     * @param ids    删除组织集合
     * @param userId 操作用户id
     * @param appId  平台ID
     * @return 返回是否成功
     */
    Boolean removeUimOrganByIds(List<Long> ids, Long userId, Long appId);


    /**
     * 根据ID修改组织信息
     *
     * @param id                组织ID
     * @param uimOrganUpdateDTO 修改信息
     * @param userId            操作用户id
     * @param appId             平台ID
     * @return 返回修改后信息
     */
    UimOrganVO updateUimOrganById(Long id, UimOrganUpdateDTO uimOrganUpdateDTO, Long userId, Long appId);

    /**
     * 根据平台ID树形展示组织
     *
     * @param pId   父组织ID
     * @param appId 平台ID
     * @return 返回树形组织
     */
    List<UimOrganNodeVO> treeUimOrganNodeByPid(Long pId, Long appId);

    /**
     * 根据ID查询组织信息
     *
     * @param id    组织ID
     * @param appId 平台ID
     * @return 返回信息
     */
    UimOrganVO getUimOrganById(Long id, Long appId);

    /**
     * 根据ID查询组织信息
     *
     * @param id 组织ID
     * @return 返回信息
     */
    UimOrganVO getUimOrganById(Long id);

}
