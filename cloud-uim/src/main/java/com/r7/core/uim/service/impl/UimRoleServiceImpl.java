package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import java.util.Optional;

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
        Optional.of(getRoleByRoleCode(code))
                .map(x -> (UimRole) null)
                .orElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_CODE_IS_EXISTS));
        Optional.of(getRoleByRoleName(roleName))
                .map(x -> (UimRole) null)
                .orElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_NAME_IS_EXISTS));
        UimRole uimRole = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS));
        uimRole.toUimRole(uimRoleDto);
        uimRole.setUpdatedAt(new Date());
        uimRole.setUpdatedBy(userId);
        boolean update = updateById(uimRole);
        if (!update) {
            throw new BusinessException(UimErrorEnum.ROLE_UPDATE_ERROR);
        }
        return uimRole.toUimRoleVo();
    }

    @Override
    public UimRoleVo saveRole(UimRoleSaveDto uimRoleSaveDto, Long appId, Long organId, Long userId) {
        log.info("角色新增内容：{}, 操作用户ID：{}", uimRoleSaveDto, userId);
        String code = uimRoleSaveDto.getCode();
        String roleName = uimRoleSaveDto.getRoleName();

        Optional.of(getRoleByRoleCode(code))
                .map(x -> (UimRole) null)
                .orElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_CODE_IS_EXISTS));
        Optional.of(getRoleByRoleName(roleName))
                .map(x -> (UimRole) null)
                .orElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_NAME_IS_EXISTS));

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
            throw new BusinessException(UimErrorEnum.ROLE_SAVE_ERROR);
        }
        return getById(id).toUimRoleVo();
    }

    @Override
    @Transactional
    public Boolean removeRoleById(Long roleId, Long userId) {
        log.info("角色新增内容：{}, 操作用户ID：{}", roleId, userId);
        roleId = Option.of(roleId)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        Option.of(getById(roleId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS));
        boolean remove = removeById(roleId);
        if (!remove) {
            throw new BusinessException(UimErrorEnum.ROLE_DELETE_ERROR);
        }
        return remove;
    }

    @Override
    @Transactional
    public Boolean removeRoleByIds(List<Long> roleIds, Long userId) {
        roleIds.forEach(x -> removeRoleById(x, userId));
        return true;
    }

    @Override
    public IPage<UimRoleVo> pageRole(String search, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public UimRoleVo getRoleById(Long roleId) {
        return null;
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
        return baseMapper.selectOne(Wrappers.<UimRole>lambdaQuery().eq(UimRole::getRoleName, roleCode));
    }
}
