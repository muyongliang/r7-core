package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.dto.UimResourceSaveDto;
import com.r7.core.uim.dto.UimResourceUpdateDto;
import com.r7.core.uim.mapper.UimResourceMapper;
import com.r7.core.uim.model.UimResource;
import com.r7.core.uim.service.UimResourceService;
import com.r7.core.uim.vo.UimResourceNodeVo;
import com.r7.core.uim.vo.UimResourceVo;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 资源服务实现
 *
 * @author zhongpingli
 */
@Slf4j
@Service
public class UimResourceServiceImpl extends ServiceImpl<UimResourceMapper, UimResource> implements UimResourceService {


    @Override
    public UimResourceVo saveUimResource(UimResourceSaveDto uimResourceSaveDto, Long appId, Long userId) {
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
            log.error("平台:{}新增资源内容失败:{},操作用户:{}", appId, uimResourceSaveDto, userId);
            throw new BusinessException(UimErrorEnum.RESOURCE_SAVE_ERROR);
        }
        log.info("平台:{}新增资源内容成功:{},操作用户:{}", appId, uimResourceSaveDto, userId);
        return getUimResourceById(id);
    }

    @Override
    public UimResourceVo updateUimResource(Long resourceId, UimResourceUpdateDto uimResourceSaveDto, Long userId) {
        Option.of(resourceId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_ID_IS_NULL));
        UimResource uimResource = Option.of(getById(resourceId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_IS_NOT_EXISTS));
        uimResource.toUimResource(uimResourceSaveDto);
        uimResource.setUpdatedBy(userId);
        uimResource.setUpdatedAt(new Date());
        boolean update = updateById(uimResource);
        if (!update) {
            throw new BusinessException(UimErrorEnum.RESOURCE_UPDATE_ERROR);
        }
        return getUimResourceById(resourceId);
    }

    @Override
    public boolean removeUimResource(Long resourceId, Long userId) {
        Option.of(getById(resourceId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_IS_NOT_EXISTS));
        boolean remove = removeById(resourceId);
        if (!remove) {
            throw new BusinessException(UimErrorEnum.RESOURCE_DELETE_ERROR);
        }
        return true;
    }

    @Override
    public List<UimResourceNodeVo> treeUimResource(Long appId, Long pId) {

        List<UimResource> uimResources = list(Wrappers.<UimResource>lambdaQuery()
                .select(UimResource::getId, UimResource::getPId, UimResource::getCode, UimResource::getResourceName,
                        UimResource::getType, UimResource::getUrl)
                .eq(UimResource::getAppId, appId).orderByAsc(UimResource::getType).orderByAsc(UimResource::getSort));
        List<UimResourceNodeVo> uimResourceNodeVos = Lists.newArrayList();
        treeUimResource(uimResourceNodeVos, uimResources, pId);
        return uimResourceNodeVos;
    }


    /**
     * 树形展示逻辑
     *
     * @param uimResourceNodeVos 展示视图
     * @param uimResources       资源数据来源
     * @param pId                资源父类
     */
    public void treeUimResource(List<UimResourceNodeVo> uimResourceNodeVos, List<UimResource> uimResources, Long pId) {

        uimResources.stream().filter(x -> x.getPId().equals(pId)).forEach(x -> {
            UimResourceNodeVo resourceNodeVo = x.toUimResourceNodeVo();
            uimResourceNodeVos.add(resourceNodeVo);
            treeUimResource(resourceNodeVo.getSubNodes(), uimResources, x.getId());
        });
    }

    @Override
    public UimResourceVo getUimResourceById(Long resourceId) {
        Option.of(resourceId)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_IS_NOT_EXISTS));
        UimResource uimResource = Option.of(getById(resourceId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.RESOURCE_IS_NOT_EXISTS));
        return uimResource.toUimResourceVo();
    }
}
