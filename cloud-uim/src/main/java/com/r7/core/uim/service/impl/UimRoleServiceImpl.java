package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.dto.UimRoleDTO;
import com.r7.core.uim.dto.UimRoleSaveDTO;
import com.r7.core.uim.mapper.UimRoleMapper;
import com.r7.core.uim.model.UimRole;
import com.r7.core.uim.service.UimRoleService;
import com.r7.core.uim.vo.UimRoleVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public UimRoleVO updateRoleById(Long id, UimRoleDTO uimRoleDto, Long userId, Long appId, Long organId) {
        log.info("角色修改ID:{},修改内容：{}, 操作用户ID：{}", id, uimRoleDto, userId);
        String code = uimRoleDto.getCode();
        String roleName = uimRoleDto.getRoleName();

        // 校验
        Option.of(id)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));

        Option.of(getRoleByRoleCode(code, appId, organId)).exists(x -> {
            throw new BusinessException(UimErrorEnum.ROLE_CODE_IS_EXISTS);
        });

        Option.of(getRoleByRoleName(roleName, appId, organId)).exists(x -> {
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
    @Transactional
    public UimRoleVO saveRole(UimRoleSaveDTO uimRoleSaveDto, Long appId, Long organId, Long userId) {
        log.info("角色新增内容：{}, 操作用户ID：{}", uimRoleSaveDto, userId);
        String code = uimRoleSaveDto.getCode();
        String roleName = uimRoleSaveDto.getRoleName();

        Option.of(getRoleByRoleCode(code, appId, organId)).exists(x -> {
            throw new BusinessException(UimErrorEnum.ROLE_CODE_IS_EXISTS);
        });

        Option.of(getRoleByRoleName(roleName, appId, organId)).exists(x -> {
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
        return getRoleById(id, appId, organId);
    }

    @Override
    @Transactional
    public Boolean removeRoleById(Long roleId, Long userId, Long appId, Long organId) {
        log.info("删除角色内容：{}, 操作用户ID：{}", roleId, userId);
        roleId = Option.of(roleId)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        UimRoleVO uimRoleVO = Option.of(getRoleById(roleId, appId, organId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS));
        boolean remove = removeById(roleId);
        if (!remove) {
            log.info("删除角色:{}内容失败：{}, 操作用户ID：{}", roleId, uimRoleVO, userId);
            throw new BusinessException(UimErrorEnum.ROLE_DELETE_ERROR);
        }
        log.info("删除角色:{}内容成功：{}, 操作用户ID：{}", roleId, uimRoleVO, userId);
        return true;
    }

    @Override
    @Transactional
    public Boolean removeRoleByIds(List<Long> roleIds, Long userId, Long appId, Long organId) {
        log.info("批量删除角色{}, 操作用户ID：{}", roleIds, userId);
        roleIds.forEach(x -> removeRoleById(x, userId, appId, organId));
        log.info("批量删除角色{}成功, 操作用户ID：{}", roleIds, userId);
        return true;
    }

    @Override
    public IPage<UimRoleVO> pageRole(String search, Long appId, Long organId, long pageNum, long pageSize) {
        Page<UimRoleVO> page = new Page<>(pageNum, pageSize);
        return baseMapper.pageRole(search, organId, appId, page);
    }

    @Override
    public UimRoleVO getRoleById(Long roleId, Long appId, Long organId) {
        roleId = Option.of(roleId)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        // todo 返回异常是否合理？
        return Option.of(getOne(Wrappers.<UimRole>lambdaQuery()
                .eq(UimRole::getId, roleId)
                .eq(UimRole::getOrganId, organId)
                .eq(UimRole::getAppId, appId)))
                .map(UimRole::toUimRoleVo)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS));
    }

    @Override
    public UimRole getRoleByRoleName(String roleName, Long appId, Long organId) {
        roleName = Option.of(roleName)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_NAME_IS_NULL));
        return baseMapper.selectOne(Wrappers.<UimRole>lambdaQuery()
                .eq(UimRole::getRoleName, roleName)
                .eq(UimRole::getOrganId, organId)
                .eq(UimRole::getAppId, appId));
    }

    @Override
    public UimRole getRoleByRoleCode(String roleCode, Long appId, Long organId) {
        roleCode = Option.of(roleCode)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_CODE_IS_NULL));
        return baseMapper.selectOne(Wrappers.<UimRole>lambdaQuery()
                .eq(UimRole::getCode, roleCode)
                .eq(UimRole::getOrganId, organId)
                .eq(UimRole::getAppId, appId));
    }

    @Override
    public List<UimRoleVO> listUimRoleByIds(List<Long> roleIds, Long appId) {
        // todo 返回异常需要添加
        Option.of(roleIds)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS));
        return Option.of(
                list(Wrappers.<UimRole>lambdaQuery()
                        .select(UimRole::getId, UimRole::getOrganId,
                                UimRole::getCode, UimRole::getRoleName,
                                UimRole::getFeature, UimRole::getSort)
                        .eq(UimRole::getAppId, appId)
                        .in(UimRole::getId, roleIds)))
                .filter(x -> x.size() > 0)
                .map(x -> x.stream()
                        .map(UimRole::toUimRoleVo)
                        .collect(Collectors.toList()))
                .getOrNull();
    }

    @Override
    public List<String> listRoleCode(List<Long> roleIds) {
        Option.of(roleIds)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS));
        List<UimRole> list = list(Wrappers.<UimRole>lambdaQuery()
                .select(UimRole::getCode)
                .in(UimRole::getId, roleIds));

        List<String> listRoleCode = list.stream().map(UimRole::getCode).collect(Collectors.toList());

        return listRoleCode;

    }
}
