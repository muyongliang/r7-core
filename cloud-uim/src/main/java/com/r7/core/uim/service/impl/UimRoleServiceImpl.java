package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.dto.UimRoleDto;
import com.r7.core.uim.dto.UimRoleSaveDto;
import com.r7.core.uim.mapper.UimRoleMapper;
import com.r7.core.uim.model.UimRole;
import com.r7.core.uim.service.UimRoleService;
import com.r7.core.uim.vo.UimRoleVo;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 角色实现层
 *
 * @author zhongpingli
 * @date 2020-09-25
 */
@Slf4j
@Service
public class UimRoleServiceImpl extends ServiceImpl<UimRoleMapper, UimRole> implements UimRoleService {


    @Override
    public UimRoleVo updateRoleById(Long id, UimRoleDto uimRoleDto, Long userId) {
        log.info("角色修改ID:{},修改内容：{}, 操作用户ID：{}", id, uimRoleDto, userId);
        String code = uimRoleDto.getCode();
        String roleName = uimRoleDto.getRoleName();

        // 校验
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));

        Option.of(getRoleByRoleCode(code)).exists(x -> {
            throw new BusinessException(UimErrorEnum.ROLE_CODE_IS_EXISTS);
        });

        Option.of(getRoleByRoleCode(roleName)).exists(x -> {
            throw new BusinessException(UimErrorEnum.ROLE_NAME_IS_EXISTS);
        });

        UimRole uimRole = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS));
        uimRole.toUimRole(uimRoleDto);
        uimRole.setUpdatedAt(new Date());
        uimRole.setUpdatedBy(userId);
        boolean update = updateById(uimRole);
        if (!update) {
            log.info("角色修改ID:{},修改内容：{}失败, 操作用户ID：{}", id, uimRole, userId);
            throw new BusinessException(UimErrorEnum.ROLE_UPDATE_ERROR);
        }
        log.info("角色修改ID:{},修改内容：{}成功, 操作用户ID：{}", id, uimRole, userId);
        return uimRole.toUimRoleVo();
    }

    @Override
    public UimRoleVo saveRole(UimRoleSaveDto uimRoleSaveDto, Long appId, Long organId, Long userId) {
        log.info("角色新增内容：{}, 操作用户ID：{}", uimRoleSaveDto, userId);
        String code = uimRoleSaveDto.getCode();
        String roleName = uimRoleSaveDto.getRoleName();

        Option.of(getRoleByRoleCode(code)).exists(x -> {
            throw new BusinessException(UimErrorEnum.ROLE_CODE_IS_EXISTS);
        });

        Option.of(getRoleByRoleCode(roleName)).exists(x -> {
            throw new BusinessException(UimErrorEnum.ROLE_NAME_IS_EXISTS);
        });
        Long id = SnowflakeUtil.getSnowflakeId();
        UimRole uimRole = new UimRole();
        uimRole.setId(id);
        uimRole.toUimRole(uimRoleSaveDto);
        uimRole.setAppId(appId);
        uimRole.setOrganId(organId);
        uimRole.setCreatedAt(new Date());
        uimRole.setCreatedBy(userId);
        uimRole.setUpdatedAt(new Date());
        uimRole.setUpdatedBy(userId);
        boolean save = save(uimRole);
        if (!save) {
            log.info("角色新增内容：{}失败, 操作用户ID：{}", uimRole, userId);
            throw new BusinessException(UimErrorEnum.ROLE_SAVE_ERROR);
        }
        log.info("新增角色，内容：{}成功, 操作用户ID：{}", uimRole, userId);
        return getRoleById(id);
    }

    @Override
    @Transactional
    public Boolean removeRoleById(Long roleId, Long userId) {
        log.info("删除角色内容：{}, 操作用户ID：{}", roleId, userId);
        roleId = Option.of(roleId)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        UimRole uimRole = Option.of(getById(roleId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS));
        boolean remove = removeById(roleId);
        if (!remove) {
            log.info("删除角色:{}内容失败：{}, 操作用户ID：{}", roleId, uimRole, userId);
            throw new BusinessException(UimErrorEnum.ROLE_DELETE_ERROR);
        }
        log.info("删除角色:{}内容成功：{}, 操作用户ID：{}", roleId, uimRole, userId);
        return true;
    }

    @Override
    @Transactional
    public Boolean removeRoleByIds(List<Long> roleIds, Long userId) {
        log.info("批量删除角色{}, 操作用户ID：{}", roleIds, userId);
        roleIds.forEach(x -> removeRoleById(x, userId));
        log.info("批量删除角色{}成功, 操作用户ID：{}", roleIds, userId);
        return true;
    }

    @Override
    public IPage<UimRoleVo> pageRole(String search, long pageNum, long pageSize) {
        Page<UimRoleVo> page = new Page<>(pageNum, pageSize);
        return baseMapper.pageRole(search, page);
    }

    @Override
    public UimRoleVo getRoleById(Long roleId) {
        roleId = Option.of(roleId)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        UimRole uimRole = Option.of(getById(roleId)).getOrElse(new UimRole());
        return uimRole.toUimRoleVo();
    }

    @Override
    public UimRole getRoleByRoleName(String roleName) {
        roleName = Option.of(roleName)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_NAME_IS_NULL));
        return baseMapper.selectOne(Wrappers.<UimRole>lambdaQuery().eq(UimRole::getRoleName, roleName));
    }

    @Override
    public UimRole getRoleByRoleCode(String roleCode) {
        roleCode = Option.of(roleCode)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_CODE_IS_NULL));
        return baseMapper.selectOne(Wrappers.<UimRole>lambdaQuery().eq(UimRole::getCode, roleCode));
    }
}
