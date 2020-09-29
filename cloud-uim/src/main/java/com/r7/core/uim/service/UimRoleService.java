package com.r7.core.uim.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.dto.UimRoleDto;
import com.r7.core.uim.dto.UimRoleSaveDto;
import com.r7.core.uim.model.UimRole;
import com.r7.core.uim.vo.UimRoleVo;

import java.util.List;

/**
 * 角色服务
 *
 * @author zhongpingli
 * @date 2020-09-25
 */
public interface UimRoleService extends IService<UimRole> {


    /**
     * 根据ID修改角色
     *
     * @param id         角色ID
     * @param uimRoleDto 角色修改谢谢
     * @param userId     操作用户ID
     * @return 返回修改结果
     */
    UimRoleVo updateRoleById(Long id,
                             UimRoleDto uimRoleDto,
                             Long userId);


    /**
     * 新增角色
     *
     * @param uimRoleSaveDto 新增角色信息
     * @param organId        组织id
     * @param appId          平台id
     * @param userId         操作用户ID
     * @return 返回新增结果
     */
    UimRoleVo saveRole(UimRoleSaveDto uimRoleSaveDto, Long appId, Long organId, Long userId);


    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @param userId 操作用户ID
     * @return 返回删除结果
     */
    Boolean removeRoleById(Long roleId, Long userId);


    /**
     * 批量删除角色
     *
     * @param roleIds 删除角色id列表
     * @param userId  操作用户id
     * @return 返回删除结果
     */
    Boolean removeRoleByIds(List<Long> roleIds, Long userId);


    /**
     * 分页展示角色
     *
     * @param search   搜索条件
     * @param pageNum  当前页数
     * @param pageSize 展示条数
     * @return 返回结果
     */
    IPage<UimRoleVo> pageRole(String search, long pageNum, long pageSize);


    /**
     * 根据ID获取角色详情
     *
     * @param roleId 角色ID
     * @return 返回结果
     */
    UimRoleVo getRoleById(Long roleId);

    /**
     * 根据角色名称查询角色
     *
     * @param roleName 角色名称
     * @return 返回信息
     */
    UimRole getRoleByRoleName(String roleName);

    /**
     * 根据角色编码查询角色
     *
     * @param roleCode 角色编码
     * @return 返回角色信息
     */
    UimRole getRoleByRoleCode(String roleCode);

}
