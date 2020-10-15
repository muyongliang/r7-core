package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.model.UimRoleResource;
import com.r7.core.uim.vo.UimRoleResourceBindVo;

import java.util.List;

/**
 * 角色资源服务层
 *
 * @author zhongpingli
 */
public interface UimRoleResourceService extends IService<UimRoleResource> {


    /**
     * 根据角色ID绑定角色资源
     *
     * @param roleId     角色ID
     * @param resourceId 资源ID
     * @param appId      平台ID
     * @param organId    组织ID
     * @param userId     操作用户ID
     * @return 返回是否成功
     */
    Boolean bindResourceByRoleId(Long roleId, Long resourceId, Long appId, Long organId, Long userId);


    /**
     * 根据角色ID解绑定角色资源
     *
     * @param roleId     角色ID
     * @param resourceId 资源ID
     * @param appId      平台ID
     * @param organId    组织ID
     * @param userId     操作用户ID
     * @return 返回是否成功
     */
    Boolean unBindResourceByRoleId(Long roleId, Long resourceId, Long appId, Long organId, Long userId);

    /**
     * 根据角色ID批量绑定角色资源
     *
     * @param roleId      角色ID
     * @param resourceIds 绑定资源ID集合
     * @param appId       平台ID
     * @param organId     组织ID
     * @param userId      用户ID
     * @return 返回是否成功
     */
    Boolean bindResourceByRoleId(Long roleId, List<Long> resourceIds, Long appId, Long organId, Long userId);

    /**
     * 根据角色ID批量解绑角色资源
     *
     * @param roleId      角色ID
     * @param resourceIds 解绑资源ID集合
     * @param appId       平台ID
     * @param organId     组织ID
     * @param userId      用户ID
     * @return 返回是否成功
     */
    Boolean unBindResourceByRoleId(Long roleId, List<Long> resourceIds, Long appId, Long organId, Long userId);

    /**
     * 根据角色ID解绑所有资源
     *
     * @param roleId  角色ID
     * @param appId   平台ID
     * @param organId 组织ID
     * @param userId  用户ID
     * @return 返回结果
     */
    Boolean unBindResourceByRoleId(Long roleId, Long appId, Long organId, Long userId);

    /**
     * 根据角色ID查询绑定的资源
     *
     * @param roleId  角色ID
     * @param organId 组织ID
     * @param appId   平台ID
     * @return 返回绑定资源信息
     */
    List<UimRoleResourceBindVo> listUimRoleResource(Long roleId, Long organId, Long appId);

    /**
     * 根据角色ID与资源ID查询绑定信息
     *
     * @param roleId     角色ID
     * @param resourceId 资源ID
     * @return 返回信息
     */
    UimRoleResource getUimRoleResourceByRoleIdAndResourceId(Long roleId, Long resourceId);


    /**
     * 根据角色编码获取对应资源的url
     *
     * @param roleCodes 角色编码
     * @return URL资源集合
     */
    List<String> listResourceUrlByRoleCodes(List<String> roleCodes);


    /**
     * 根据角色ID获取对应的资源URL
     *
     * @param roleIds 角色ID集合
     * @return 返回资源url集合
     */
    List<String> listResourceUrlByRoleIds(List<Long> roleIds);


}
