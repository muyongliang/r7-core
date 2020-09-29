package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
            throw new BusinessException(UimErrorEnum.RESOURCE_SAVE_ERROR);
        }

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


        return null;
    }


    public void treeUimResource(UimResourceNodeVo uimResourceNodeVo, Long appId) {
        List<UimResource> uimResources = list(Wrappers.<UimResource>lambdaQuery()
                .select(UimResource::getId, UimResource::getPId, UimResource::getCode, UimResource::getResourceName,
                        UimResource::getType, UimResource::getUrl)
                .eq(UimResource::getAppId, appId).eq(UimResource::getPId, uimResourceNodeVo.getId()));

        uimResources.forEach(x -> {
            UimResourceNodeVo resourceNodeVo = x.toUimResourceNodeVo();
            uimResourceNodeVo.addSubNode(resourceNodeVo);
            treeUimResource(resourceNodeVo, appId);
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
