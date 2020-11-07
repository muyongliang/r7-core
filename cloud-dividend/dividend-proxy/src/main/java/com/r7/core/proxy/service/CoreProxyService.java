package com.r7.core.proxy.service;

import com.r7.core.proxy.dto.CoreProxyDTO;
import com.r7.core.proxy.dto.CoreProxyUpdateDTO;
import com.r7.core.proxy.model.CoreProxy;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.proxy.vo.CoreProxyNodeVO;
import com.r7.core.proxy.vo.CoreProxyVO;

import java.util.List;

/**
 * 
 * @Description 代理层级service接口层
 * @author wutao
 *
 */
public interface CoreProxyService extends IService<CoreProxy>{

    /**
     * 新增层级
//     * @param coreProxyDto 新增的层级信息
     * @param  optionalUserId 操作人ID
     * @return 返回新增结果
     */
    CoreProxyVO saveCoreProxy(CoreProxyDTO coreProxyDto , Long optionalUserId);

    /**
     * 修改层级(只能后端管理员用)
     * @param id 层级id
     * @param coreProxyUpdateDto 要修改的层级信息
     * @param optionalUserId 操作者
     * @return 返回修改结果
     */
    CoreProxyVO updateCoreProxy(Long id, CoreProxyUpdateDTO coreProxyUpdateDto, Long optionalUserId);

    /**
     * 根据层级id查询层级信息
     * @param id 层级id
     * @param organId 组织id
     * @return 返回查询结果
     */
    CoreProxyVO getCoreProxyById(Long id,Long organId);
    /**
     * 根据用户id查询用户的层级信息
     * @param userId 用户id
     * @param organId 组织id
     * @return 返回查询结果
     */
    CoreProxyVO getCoreProxyByUserId(Long userId, Long organId);

    /**
     * 根据用户父id查询用户的直接下级层级信息
     * @param parentId 用户父id
     * @return 返回查询结果
     */
    List<CoreProxyVO> getCoreProxyByParentId(Long parentId);

    /**
     * 树形展示层级
     * @param pId   父ID
     * @param organId 组织ID
     * @return 返回结果
     */
    List<CoreProxyNodeVO> treeCoreProxyByPid(Long pId, Long organId);


    /**
     * 统计当前用户的所有下级人数
     * @param userId 用户id
     * @param organId 组织id
     * @return 返回统计结果
     */
    int countSubordinateNumByUserId(Long userId,Long organId);

    /**
     * 修改当前用户的下级人数
     * @param userId 当前用户ID
     * @param subordinateNum 下级人数
     * @param organId 组织id
     * @param optionalUserId 操作者
     * @return 返回修改结果
     */
    CoreProxyVO updateSubordinateNumById(Long userId, Integer subordinateNum, Long organId, Long optionalUserId);

    /**
     * 根据层级ID修改该层级的父ID
     * @param id 层级id
     * @param userId 指定层级的用户id
     * @param organId 组织id
     * @param optionalUserId 操作者id
     * @return 修改结果
     */
    CoreProxyVO updateCoreProxyPidById(Long id , Long userId ,Long organId, Long optionalUserId);

    /**
     * 计算指定节点的层级值
     * @param userId 指定节点的用户id
     *
     * @param organId 组织id
     * @return 返回计算的层级值
     */
    int countLevel(Long userId,Long organId);

    /**
     * 根据层级id修改层级值
     * @param id 层级id
     * @param level 目标层级
     * @param organId 组织id
     * @param optionalUserId 操作者
     * @return 返回修改结果
     */
    CoreProxyVO updateCoreProxyLevelById(Long id, Integer level ,Long organId, Long optionalUserId);

    /**
     * 重新计算并修改指定组织下节点的层级值及下级人数
     * @param organId 组织ID
     * @param optionalUserId 操作者
     * @return 返回相应结果
     */
    List<CoreProxyVO> updateAndCountCoreProxyLevelByOrganId(Long organId, Long optionalUserId);

    /**
     * 根据组织id查询该组织下的所有层级
     * @param organId 组织ID
     * @return 返回查询结果
     */
    List<CoreProxy> getAllCountCoreProxyByOrganId(Long organId);

    /**
     * 把某个节点修改到指定节点下面
     * @param id 移动的节点ID
     * @param userId 指定节点的用户id，作为父ID
     * @param organId 组织id
     * @param optionalUserId 操作者
     * @return 返回修改后的树形层级
     */
    List<CoreProxyNodeVO> updateCoreProxyLevel(Long id, Long userId,Long organId, Long optionalUserId);

    /**
     * 根据组织id，用户id得到该用户下的所有下级的集合
     * @param userId 用户id
     * @param organId 组织id
     * @return 查询结果
     */
    List<CoreProxyVO> getAllSubordinateCoreProxy(Long userId, Long organId);

    /**
     * 验证userId所在层级是否存在于id所在层级的下级集合中
     * @param id 层级id
     * @param userId 指定层级的用户id
     * @param organId 组织id
     * @return 验证结果
     */
    boolean checkCoreProxyIsExistList(Long id, Long userId,Long organId);

    /**
     * 对传过来的各种id进行长度验证
     * @param id 用户id/组织id等等
     * @return 验证结果
     */
    boolean checkIdLength(Long id);
}
