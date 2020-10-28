package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.r7.core.cache.constant.PushType;
import com.r7.core.cache.service.RedisListService;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.RedisConstant;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.mapper.UimRoleResourceMapper;
import com.r7.core.uim.model.UimRoleResource;
import com.r7.core.uim.service.UimResourceService;
import com.r7.core.uim.service.UimRoleResourceService;
import com.r7.core.uim.service.UimRoleService;
import com.r7.core.uim.vo.*;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色资源服务实现层
 *
 * @author zhongpingli
 */
@Slf4j
@Service
public class UimRoleResourceServiceImpl extends ServiceImpl<UimRoleResourceMapper, UimRoleResource> implements UimRoleResourceService {


    @Resource
    private UimRoleService uimRoleService;

    @Resource
    private UimResourceService uimResourceService;

    @Resource
    private RedisListService redisListService;

    /**
     * 初始化资源对应的角色
     */
    @PostConstruct
    public void init() {
        List<UimRoleResourceVO> uimRoleResourceVos = baseMapper.listUimRoleResourceVo();
        redisListService.removeByKey(RedisConstant.REDIS_RESOURCE_ROLE_KEY);
        redisListService.addListValue(RedisConstant.REDIS_RESOURCE_ROLE_KEY,
                uimRoleResourceVos, PushType.LEFT);
    }

    @Override
    @Transactional
    public Boolean bindResourceByRoleId(Long roleId, Long resourceId, Long appId, Long organId, Long userId) {
        log.info("平台:{}对组织:{}中角色:{}绑定资源:{},操作用户:{}。", appId, organId, roleId, resourceId, userId);
        // 验证
        uimRoleService.getRoleById(roleId, appId, organId);
        uimResourceService.getUimResourceById(resourceId, appId);
        Option.of(getUimRoleResourceByRoleIdAndResourceId(roleId, resourceId)).exists(x -> {
            throw new BusinessException(UimErrorEnum.ROLE_RESOURCE_IS_NOT_EXISTS);
        });

        Long id = SnowflakeUtil.getSnowflakeId();
        UimRoleResource uimRoleResource = new UimRoleResource();
        uimRoleResource.setId(id);
        uimRoleResource.setRoleId(roleId);
        uimRoleResource.setResourceId(resourceId);
        uimRoleResource.setCreatedAt(new Date());
        uimRoleResource.setCreatedBy(userId);
        uimRoleResource.setUpdatedAt(new Date());
        uimRoleResource.setUpdatedBy(userId);
        boolean save = save(uimRoleResource);
        if (!save) {
            log.info("平台:{}中组织:{}对角色:{}绑定资源:{}失败,操作用户:{}", appId, organId, roleId, resourceId, userId);
            throw new BusinessException(UimErrorEnum.ROLE_RESOURCE_BIND_ERROR);
        }
        log.info("平台:{}中组织:{}对角色:{}绑定资源:{}成功,操作用户:{}", appId, organId, roleId, resourceId, userId);
        return true;
    }

    @Override
    @Transactional
    public Boolean unBindResourceByRoleId(Long roleId, Long resourceId, Long appId, Long organId, Long userId) {
        log.info("平台:{}对组织:{}中角色:{}解绑资源:{},操作用户:{}。", appId, organId, roleId, resourceId, userId);
        // 验证
        Option.of(uimRoleService.getRoleById(roleId, appId, organId)).getOrElseThrow(() ->
                new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS));
        Option.of(uimResourceService.getUimResourceById(resourceId, appId)).getOrElseThrow(() ->
                new BusinessException(UimErrorEnum.RESOURCE_IS_NOT_EXISTS));
        UimRoleResource uimRoleResource = Option.of(getUimRoleResourceByRoleIdAndResourceId(roleId, resourceId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_RESOURCE_IS_EXISTS));
        // 删除
        boolean remove = removeById(uimRoleResource.getId());
        if (!remove) {
            log.info("平台:{}中组织:{}对角色:{}解绑资源:{}失败,操作用户:{}", appId, organId, roleId, resourceId, userId);
            throw new BusinessException(UimErrorEnum.ROLE_RESOURCE_UNBIND_ERROR);
        }
        log.info("平台:{}中组织:{}对角色:{}解绑资源:{}成功,操作用户:{}", appId, organId, roleId, resourceId, userId);
        return true;
    }

    @Override
    @Transactional
    public Boolean bindResourceByRoleId(Long roleId, List<Long> resourceIds, Long appId, Long organId, Long userId) {
        Option.of(roleId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        Option.of(resourceIds).getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_ID_IS_NULL));
        // 先解绑所有
        unBindResourceByRoleId(roleId, appId, organId, userId);
        // 再绑定
        resourceIds.forEach(x -> bindResourceByRoleId(roleId, x, appId, organId, userId));
        // 重新缓存
        init();
        return true;
    }

    @Override
    @Transactional
    public Boolean unBindResourceByRoleId(Long roleId, List<Long> resourceIds, Long appId, Long organId, Long userId) {
        Option.of(roleId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        Option.of(resourceIds).getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_ID_IS_NULL));
        resourceIds.forEach(x -> unBindResourceByRoleId(roleId, x, appId, organId, userId));
        return true;
    }

    @Override
    @Transactional
    public Boolean unBindResourceByRoleId(Long roleId, Long appId, Long organId, Long userId) {
        log.info("平台:{}对组织:{}中角色:{}解绑所有资源,操作用户:{}。", appId, organId, roleId, userId);
        uimRoleService.getRoleById(roleId, appId, organId);
        List<UimRoleResource> uimRoleResourceList =
                list(Wrappers.<UimRoleResource>lambdaQuery().eq(UimRoleResource::getRoleId, roleId));
        if (uimRoleResourceList == null || uimRoleResourceList.size() == 0) {
            return true;
        }
        List<Long> ids = uimRoleResourceList.stream().map(UimRoleResource::getId).collect(Collectors.toList());
        boolean remove = removeByIds(ids);
        if (!remove) {
            log.info("平台:{}对组织:{}中角色:{}解绑所有资源:{}失败,操作用户:{}。", appId, organId, roleId, ids, userId);
            throw new BusinessException(UimErrorEnum.ROLE_RESOURCE_UNBIND_ERROR);
        }
        log.info("平台:{}对组织:{}中角色:{}解绑所有资源:{}成功,操作用户:{}。", appId, organId, roleId, ids, userId);
        return true;
    }

    @Override
    public List<UimRoleResourceBindVo> listUimRoleResource(Long roleId, Long organId, Long appId) {
        uimRoleService.getRoleById(roleId, appId, organId);
        List<UimRoleResource> uimRoleResourceList =
                list(Wrappers.<UimRoleResource>lambdaQuery().eq(UimRoleResource::getRoleId, roleId));
        if (uimRoleResourceList == null || uimRoleResourceList.size() == 0) {
            return null;
        }
        List<Long> resourceIds = uimRoleResourceList.stream()
                .map(UimRoleResource::getResourceId)
                .collect(Collectors.toList());
        List<UimResourceVO> uimResourceVOList = uimResourceService.listUimResourceByIds(resourceIds, appId);
        if (uimResourceVOList == null || uimResourceVOList.size() == 0) {
            return null;
        }
        Map<Long, UimResourceVO> uimResourceVoMap = uimResourceVOList.stream()
                .collect(Collectors.toMap(UimResourceVO::getId, x -> x));
        List<UimRoleResourceBindVo> uimRoleResourceBindVoList = Lists.newArrayList();
        uimRoleResourceList.forEach(x -> {
            UimRoleResourceBindVo uimRoleResourceBindVo = new UimRoleResourceBindVo();
            uimRoleResourceBindVo.setRoleId(x.getRoleId());
            uimRoleResourceBindVo.setResourceId(x.getResourceId());
            uimRoleResourceBindVo.setResourceCode(uimResourceVoMap.get(x.getResourceId()).getCode());
            uimRoleResourceBindVo.setResourceName(uimResourceVoMap.get(x.getResourceId()).getResourceName());
            uimRoleResourceBindVo.setResourceUrl(uimResourceVoMap.get(x.getResourceId()).getUrl());
            uimRoleResourceBindVoList.add(uimRoleResourceBindVo);
        });
        return uimRoleResourceBindVoList;
    }

    @Override
    public UimRoleResource getUimRoleResourceByRoleIdAndResourceId(Long roleId, Long resourceId) {
        Option.of(roleId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        Option.of(resourceId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_ID_IS_NULL));
        return getOne(Wrappers.<UimRoleResource>lambdaQuery()
                .eq(UimRoleResource::getRoleId, roleId)
                .eq(UimRoleResource::getResourceId, resourceId));
    }

    @Override
    public List<UimResourceInfoVo> listResourceUrlByRoleCodes(List<String> roleCodes) {
        List<Long> roleIds = uimRoleService.listRoleIdsByRoleCods(roleCodes);
        if (roleIds == null || roleIds.size() == 0) {
            return null;
        }
        return listResourceUrlByRoleIds(roleIds);
    }

    @Override
    public List<UimResourceInfoVo> listResourceUrlByRoleIds(List<Long> roleIds) {
        Option.of(roleIds).getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        List<UimRoleResource> roleResources = list(Wrappers.<UimRoleResource>lambdaQuery()
                .select(UimRoleResource::getResourceId)
                .in(UimRoleResource::getRoleId, roleIds));
        if (roleResources == null || roleResources.size() == 0) {
            return null;
        }
        List<Long> resourceIds = roleResources.stream().map(UimRoleResource::getResourceId).collect(Collectors.toList());
        return uimResourceService.listResourceUrlsByIds(resourceIds);
    }
}
