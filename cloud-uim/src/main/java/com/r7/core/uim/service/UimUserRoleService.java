package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.model.UimUserRole;
import com.r7.core.uim.vo.UimUserRoleBindVO;

import java.util.List;

/**
 * @author zs
 * @description: 用户角色服务层
 * @date : 2020-10-12
 */
public interface UimUserRoleService extends IService<UimUserRole> {
    /**
     * 根据用户ID绑定角色
     *
     * @param roleId     角色ID
     * @param bindUserId 绑定用户ID
     * @param appId      平台ID
     * @param organId    组织ID
     * @param userId     操作用户ID
     * @return 返回是否成功
     */
    Boolean bindRoleByUserId(Long roleId, Long bindUserId, Long appId, Long organId, Long userId);


    /**
     * 根据用户ID解绑定角色
     *
     * @param roleId       角色ID
     * @param unBindUserId 解绑用户ID
     * @param appId        平台ID
     * @param organId      组织ID
     * @param userId       操作用户ID
     * @return 返回是否成功
     */
    Boolean unBindRoleByUserId(Long roleId, Long unBindUserId, Long appId, Long organId, Long userId);

    /**
     * 根据用户ID批量绑定角色
     *
     * @param bindUserId  绑定用户ID
     * @param roleIds     绑定角色ID集合
     * @param appId       平台ID
     * @param organId     组织ID
     * @param userId      用户ID
     * @return 返回是否成功
     */
    Boolean bindRoleByUserId(Long bindUserId, List<Long> roleIds, Long appId, Long organId, Long userId);

    /**
     * 根据用户ID批量解绑角色
     *
     * @param unBindUserId 解绑用户ID
     * @param roleIds      解绑角色ID集合
     * @param appId        平台ID
     * @param organId      组织ID
     * @param userId       用户ID
     * @return 返回是否成功
     */
    Boolean unBindRoleByUserId(Long unBindUserId, List<Long> roleIds, Long appId, Long organId, Long userId);

    /**
     * 根据用户ID解绑所有角色
     *
     * @param unBindUserId  解绑用户ID
     * @param appId         平台ID
     * @param organId       组织ID
     * @param userId        用户ID
     * @return 返回结果
     */
    Boolean unBindRoleByUserId(Long unBindUserId, Long appId, Long organId, Long userId);

    /**
     * 根据用户ID查询绑定的角色
     *
     * @param userId  用户ID
     * @param organId 组织ID
     * @param appId   平台ID
     * @return 返回绑定资源信息
     */
    List<UimUserRoleBindVO> listUimUserRole(Long userId, Long organId, Long appId);

    /**
     * 根据用户ID与角色ID查询绑定信息
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 返回信息
     */
    UimUserRole getUimUserRoleByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 根据用户id获取对应角色的code
     *
     * @param userId 用户id
     * @return 返回roleCode
     */
    List<String> listRoleCode(Long userId, Long appId);
}
