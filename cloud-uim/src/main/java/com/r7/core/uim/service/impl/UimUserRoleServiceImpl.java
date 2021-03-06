package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.mapper.UimUserRoleMapper;
import com.r7.core.uim.model.UimUserRole;
import com.r7.core.uim.service.UimRoleService;
import com.r7.core.uim.service.UimUserRoleService;
import com.r7.core.uim.service.UimUserService;
import com.r7.core.uim.vo.UimRoleVO;
import com.r7.core.uim.vo.UimUserRoleBindVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zs
 * @description: 用户角色服务实现层
 * @date : 2020-10-12
 */
@Slf4j
@Service
public class UimUserRoleServiceImpl extends ServiceImpl<UimUserRoleMapper, UimUserRole> implements UimUserRoleService {

    @Resource
    private UimRoleService uimRoleService;

    @Resource
    private UimUserService uimUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean bindRoleByUserId(Long bindUserId, Long roleId, Long appId, Long organId, Long userId) {
        log.info("平台:{}对组织:{}中用户:{}绑定角色:{},操作用户:{}。", appId, organId, bindUserId, roleId, userId);
        Option.of(bindUserId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NULL));
        Option.of(roleId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        int standard = 19;
        if (bindUserId.toString().length() != standard) {
            throw new BusinessException(UimErrorEnum.USER_ID_LENGTH_IS_INCORRECT);
        }
        if (roleId.toString().length() != standard) {
            throw new BusinessException(UimErrorEnum.ROLE_ID_LENGTH_INCORRECT);
        }
        Option.of(uimUserService.getUserById(bindUserId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NOT_EXIST));
        Option.of(uimRoleService.getRoleById(roleId, appId, organId)).exists(x -> {
            throw new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS);
        });

        Long id = SnowflakeUtil.getSnowflakeId();
        UimUserRole uimUserRole = new UimUserRole();
        uimUserRole.setId(id);
        uimUserRole.setRoleId(roleId);
        uimUserRole.setUserId(bindUserId);
        uimUserRole.setCreatedAt(new Date());
        uimUserRole.setCreatedBy(userId);
        uimUserRole.setUpdatedAt(new Date());
        uimUserRole.setUpdatedBy(userId);
        boolean save = save(uimUserRole);
        if (!save) {
            log.info("平台:{}对组织:{}中用户:{}绑定角色:{}失败,操作用户:{}", appId, organId, bindUserId, roleId, userId);
            throw new BusinessException(UimErrorEnum.USER_ROLE_BIND_ERROR);
        }
        log.info("平台:{}对组织:{}中用户:{}绑定角色:{}成功,操作用户:{}。", appId, organId, bindUserId, roleId, userId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean unBindRoleByUserId(Long roleId, Long unBindUserId, Long appId, Long organId, Long userId) {
        log.info("平台:{}对组织:{}中用户:{}解绑角色:{},操作用户:{}。", appId, organId, unBindUserId, roleId, userId);
        Option.of(roleId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        Option.of(unBindUserId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NULL));
        int standard = 19;
        if (roleId.toString().length() != standard) {
            throw new BusinessException(UimErrorEnum.ROLE_ID_LENGTH_INCORRECT);
        }
        if (unBindUserId.toString().length() != standard) {
            log.info("角色id:{}长度校验结果:{},校验时间:{},操作人:{}", unBindUserId, "角色id长度不正确", LocalDateTime.now(), userId);
            throw new BusinessException(UimErrorEnum.USER_ID_LENGTH_IS_INCORRECT);
        }

        Option.of(uimRoleService.getRoleById(roleId, appId, organId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS));
        UimUserRole uimUserRole = Option.of(getUimUserRoleByUserIdAndRoleId(unBindUserId, roleId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ROLE_IS_EXISTS));
        //删除
        boolean remove = removeById(uimUserRole.getId());
        if (!remove) {
            log.info("平台:{}对组织:{}中用户:{}解绑角色:{}失败,操作用户:{}。", appId, organId, unBindUserId, roleId, userId);
        }
        log.info("平台:{}对组织:{}中用户:{}解绑角色:{}成功,操作用户:{}。", appId, organId, unBindUserId, roleId, userId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean bindRoleByUserId(Long bindUserId, List<Long> roleIds, Long appId, Long organId, Long userId) {
        Option.of(bindUserId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NULL));
        Option.of(roleIds).getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        int standard = 19;
        if (bindUserId.toString().length() != standard) {
            throw new BusinessException(UimErrorEnum.USER_ID_LENGTH_IS_INCORRECT);
        }
        Option.of(uimUserService.getUserById(bindUserId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NOT_EXIST));
        //先解绑所有再绑定
        unBindRoleByUserId(bindUserId, appId, organId, userId);
        for (Long roleId : roleIds) {
            if (roleId.toString().length() != standard) {
                log.info("角色id:{}长度校验结果:{},校验时间:{},操作人:{}", roleId, "角色id长度不正确", LocalDateTime.now(), userId);
                throw new BusinessException(UimErrorEnum.ROLE_ID_LENGTH_INCORRECT);
            }
            Option.of(uimRoleService.getRoleById(roleId, appId, organId)).getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS));
            bindRoleByUserId(bindUserId, roleId, appId, organId, userId);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean unBindRoleByUserId(Long unBindUserId, List<Long> roleIds, Long appId, Long organId, Long userId) {
        Option.of(unBindUserId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NULL));
        Option.of(roleIds).getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        int standard = 19;
        if (unBindUserId.toString().length() != standard) {
            throw new BusinessException(UimErrorEnum.USER_ID_LENGTH_IS_INCORRECT);
        }
        Option.of(uimUserService.getUserById(unBindUserId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NOT_EXIST));
        roleIds.forEach(roleId -> {
            if (roleId.toString().length() != standard) {
                throw new BusinessException(UimErrorEnum.ROLE_ID_LENGTH_INCORRECT);
            }
            Option.of(uimRoleService.getRoleById(roleId, appId, organId))
                    .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS));
            unBindRoleByUserId(unBindUserId, roleId, appId, organId, userId);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean unBindRoleByUserId(Long unBindUserId, Long appId, Long organId, Long userId) {
        log.info("平台:{}对组织:{}中用户:{}解绑所有角色, 操作用户:{}。", appId, organId, unBindUserId, userId);
        Option.of(unBindUserId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NULL));
        int standard = 19;
        if (unBindUserId.toString().length() != standard) {
            throw new BusinessException(UimErrorEnum.USER_ID_LENGTH_IS_INCORRECT);
        }
        List<UimUserRole> uimUserRoleList = Option.of(list(Wrappers.<UimUserRole>lambdaQuery()
                .select(UimUserRole::getId)
                .eq(UimUserRole::getUserId, unBindUserId)))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NOT_EXIST));
        if (uimUserRoleList == null || uimUserRoleList.size() == 0) {
            return true;
        }
        List<Long> ids = uimUserRoleList.stream().map(UimUserRole::getId).collect(Collectors.toList());
        boolean remove = removeByIds(ids);
        if (!remove) {
            log.info("平台:{}对组织:{}中用户:{}解绑所有角色失败, 操作用户:{}。", appId, organId, unBindUserId, userId);
            throw new BusinessException(UimErrorEnum.USER_ROLE_UNBIND_ERROR);
        }
        log.info("平台:{}对组织:{}中用户:{}解绑所有角色成功, 操作用户:{}。", appId, organId, unBindUserId, userId);
        return true;
    }

    @Override
    public List<UimUserRoleBindVO> listUimUserRole(Long userId, Long organId, Long appId) {
        Option.of(userId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NULL));
        int standard = 19;
        if (userId.toString().length() != standard) {
            throw new BusinessException(UimErrorEnum.USER_ID_LENGTH_IS_INCORRECT);
        }
        List<UimUserRole> uimUserRoleList = Option.of(list(Wrappers.<UimUserRole>lambdaQuery()
                .select(UimUserRole::getId)
                .eq(UimUserRole::getUserId, userId)))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NOT_EXIST));
        if (uimUserRoleList == null || uimUserRoleList.size() == 0) {
            return null;
        }
        List<Long> roleIds = uimUserRoleList.stream()
                .map(UimUserRole::getRoleId)
                .collect(Collectors.toList());
        List<UimRoleVO> uimRoleVOList = uimRoleService.listUimRoleByIds(roleIds, appId);
        if (uimRoleVOList == null || uimRoleVOList.size() == 0) {
            return null;
        }
        Map<Long, UimRoleVO> uimRoleVoMap = uimRoleVOList.stream()
                .collect(Collectors.toMap(UimRoleVO::getId, x -> x));
        List<UimUserRoleBindVO> uimUserRoleBindVOList = Lists.newArrayList();

        uimUserRoleList.forEach(x -> {
            UimUserRoleBindVO uimUserRoleBindVO = new UimUserRoleBindVO();
            uimUserRoleBindVO.setUserId(x.getUserId());
            uimUserRoleBindVO.setRoleId(x.getRoleId());
            uimUserRoleBindVO.setRoleCode(uimRoleVoMap.get(x.getRoleId()).getCode());
            uimUserRoleBindVO.setRoleName(uimRoleVoMap.get(x.getRoleId()).getRoleName());
            uimUserRoleBindVOList.add(uimUserRoleBindVO);
        });
        return uimUserRoleBindVOList;
    }

    @Override
    public UimUserRole getUimUserRoleByUserIdAndRoleId(Long userId, Long roleId) {
        Option.of(userId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NULL));
        Option.of(roleId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        int standard = 19;
        if (userId.toString().length() != standard) {
            throw new BusinessException(UimErrorEnum.USER_ID_LENGTH_IS_INCORRECT);
        }
        if (roleId.toString().length() != standard) {
            throw new BusinessException(UimErrorEnum.ROLE_ID_LENGTH_INCORRECT);
        }
        return Option.of(getOne(Wrappers.<UimUserRole>lambdaQuery()
                .eq(UimUserRole::getUserId, userId)
                .eq(UimUserRole::getRoleId, roleId)))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ROLE_IS_NOT_EXISTS));
    }

    @Override
    public List<String> listRoleCode(Long userId) {
        Option.of(userId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NULL));
        int standard = 19;
        if (userId.toString().length() != standard) {
            throw new BusinessException(UimErrorEnum.USER_ID_LENGTH_IS_INCORRECT);
        }
        List<UimUserRole> uimUserRoleList = Option.of(list(Wrappers.<UimUserRole>lambdaQuery()
                .select(UimUserRole::getId)
                .eq(UimUserRole::getUserId, userId)))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NOT_EXIST));
        if (uimUserRoleList == null || uimUserRoleList.size() == 0) {
            return null;
        }
        List<Long> roleIds = Lists.newArrayList();
        for (UimUserRole uimUserRole : uimUserRoleList) {
            Long roleId = uimUserRole.getRoleId();
            roleIds.add(roleId);
        }
        List<String> listRoleCode = uimRoleService.listRoleCode(roleIds);
        return listRoleCode;
    }
}
