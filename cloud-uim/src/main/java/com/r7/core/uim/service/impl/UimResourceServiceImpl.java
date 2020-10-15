package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.dto.UimResourceUpdateDTO;
import com.r7.core.uim.mapper.UimResourceMapper;
import com.r7.core.uim.model.UimResource;
import com.r7.core.uim.dto.UimResourceSaveDTO;
import com.r7.core.uim.service.UimResourceService;
import com.r7.core.uim.vo.UimResourceNodeVO;
import com.r7.core.uim.vo.UimResourceVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资源服务实现
 *
 * @author zhongpingli
 */
@Slf4j
@Service
public class UimResourceServiceImpl extends ServiceImpl<UimResourceMapper, UimResource> implements UimResourceService {


    @Override
    @Transactional
    public UimResourceVO saveUimResource(UimResourceSaveDTO uimResourceSaveDto, Long appId, Long userId) {
        log.info("平台:{}新增资源内容:{},操作用户:{}", appId, uimResourceSaveDto, userId);
        Long id = SnowflakeUtil.getSnowflakeId();
        UimResource uimResource = new UimResource();
        uimResource.setId(id);
        uimResource.setAppId(appId);
        uimResource.toUimResource(uimResourceSaveDto);
        uimResource.setCreatedAt(new Date());
        uimResource.setCreatedBy(userId);
        uimResource.setUpdatedAt(new Date());
        uimResource.setUpdatedBy(userId);
        boolean save = save(uimResource);
        if (!save) {
            log.info("平台:{}新增资源内容失败:{},操作用户:{}", appId, uimResourceSaveDto, userId);
            throw new BusinessException(UimErrorEnum.RESOURCE_SAVE_ERROR);
        }
        log.info("平台:{}新增资源内容成功:{},操作用户:{}", appId, uimResourceSaveDto, userId);
        return getUimResourceById(id, appId);
    }

    @Override
    @Transactional
    public UimResourceVO updateUimResource(Long resourceId, UimResourceUpdateDTO uimResourceSaveDto,
                                           Long appId, Long userId) {
        log.info("修改资源:{}内容为:{},操作用户:{}", resourceId, uimResourceSaveDto, userId);
        Option.of(resourceId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_ID_IS_NULL));
        UimResource uimResource = Option.of(getById(resourceId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_IS_NOT_EXISTS));
        uimResource.toUimResource(uimResourceSaveDto);
        uimResource.setUpdatedBy(userId);
        uimResource.setUpdatedAt(new Date());
        boolean update = updateById(uimResource);
        if (!update) {
            log.info("修改资源:{}内容为:{}失败,操作用户:{}", resourceId, uimResourceSaveDto, userId);
            throw new BusinessException(UimErrorEnum.RESOURCE_UPDATE_ERROR);
        }
        log.info("修改资源:{}内容为:{}成功,操作用户:{}", resourceId, uimResourceSaveDto, userId);
        return getUimResourceById(resourceId, appId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeUimResource(Long resourceId, Long userId, Long appId) {
        log.info("删除资源:{},操作用户:{}", resourceId, userId);
        UimResourceVO uimResourceVo = Option.of(getUimResourceById(resourceId, appId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_IS_NOT_EXISTS));
        Option.of(getUimResourceByPid(resourceId))
                .exists(x -> {
                    throw new BusinessException(UimErrorEnum.RESOURCE_SUB_IS_EXISTS);
                });
        boolean remove = removeById(resourceId);
        if (!remove) {
            log.info("删除资源:{}失败,操作用户:{}", resourceId, userId);
            throw new BusinessException(UimErrorEnum.RESOURCE_DELETE_ERROR);
        }
        log.info("删除资源:{}内容为:{}成功,操作用户:{}", resourceId, uimResourceVo, userId);
        return true;
    }

    @Override
    public List<UimResourceNodeVO> treeUimResource(Long appId, Long pId) {

        List<UimResource> uimResources = list(Wrappers.<UimResource>lambdaQuery()
                .select(UimResource::getId, UimResource::getPId, UimResource::getCode, UimResource::getResourceName,
                        UimResource::getType, UimResource::getUrl)
                .eq(UimResource::getAppId, appId).orderByAsc(UimResource::getType).orderByAsc(UimResource::getSort));
        List<UimResourceNodeVO> uimresourcenodevos = Lists.newArrayList();
        treeUimResource(uimresourcenodevos, uimResources, pId);
        return uimresourcenodevos;
    }


    /**
     * 树形展示逻辑
     *
     * @param uimresourcenodevos 展示视图
     * @param uimResources       资源数据来源
     * @param pId                资源父类
     */
    public void treeUimResource(List<UimResourceNodeVO> uimresourcenodevos, List<UimResource> uimResources, Long pId) {

        uimResources.stream().filter(x -> x.getPId().equals(pId)).forEach(x -> {
            UimResourceNodeVO resourceNodeVo = x.toUimResourceNodeVo();
            uimresourcenodevos.add(resourceNodeVo);
            treeUimResource(resourceNodeVo.getSubNodes(), uimResources, x.getId());
        });
    }

    @Override
    public UimResourceVO getUimResourceById(Long resourceId, Long appId) {
        Option.of(resourceId)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_IS_NOT_EXISTS));
        UimResource uimResource = Option.of(getOne(Wrappers.<UimResource>lambdaQuery()
                .select(UimResource::getId, UimResource::getPId, UimResource::getCode, UimResource::getResourceName,
                        UimResource::getUrl, UimResource::getType, UimResource::getSort)
                .eq(UimResource::getId, resourceId)
                .eq(UimResource::getAppId, appId)))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_IS_NOT_EXISTS));
        return uimResource.toUimResourceVo();
    }

    @Override
    public List<UimResourceVO> getUimResourceByPid(Long pId) {
        Option.of(pId)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_IS_NOT_EXISTS));
        return Option.of(
                list(Wrappers.<UimResource>lambdaQuery()
                        .select(UimResource::getId, UimResource::getPId, UimResource::getCode,
                                UimResource::getResourceName, UimResource::getUrl,
                                UimResource::getType, UimResource::getSort)
                        .eq(UimResource::getPId, pId)))
                .filter(x -> x.size() > 0)
                .map(x -> x.stream()
                        .map(UimResource::toUimResourceVo)
                        .collect(Collectors.toList()))
                .getOrNull();
    }

    @Override
    public List<UimResourceVO> listUimResourceByIds(List<Long> ids, Long appId) {
        Option.of(ids)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_IS_NOT_EXISTS));
        return Option.of(
                list(Wrappers.<UimResource>lambdaQuery()
                        .select(UimResource::getId, UimResource::getPId, UimResource::getCode,
                                UimResource::getResourceName, UimResource::getUrl,
                                UimResource::getType, UimResource::getSort)
                        .eq(UimResource::getAppId, appId)
                        .in(UimResource::getId, ids)))
                .filter(x -> x.size() > 0)
                .map(x -> x.stream()
                        .map(UimResource::toUimResourceVo)
                        .collect(Collectors.toList()))
                .getOrNull();

    }

    @Override
    public List<String> listResourceUrlsByIds(List<Long> ids) {
        Option.of(ids).getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_ID_IS_NULL));
        List<UimResource> resourceList = list(Wrappers.<UimResource>lambdaQuery()
                .select(UimResource::getUrl)
                .in(UimResource::getId, ids));
        return resourceList.stream().map(UimResource::getUrl).collect(Collectors.toList());
    }
}
