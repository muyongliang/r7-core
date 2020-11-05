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
import com.r7.core.uim.dto.UimChillSaveDTO;
import com.r7.core.uim.dto.UimChillSaveListDTO;
import com.r7.core.uim.mapper.UimChillMapper;
import com.r7.core.uim.model.UimChill;
import com.r7.core.uim.service.UimChillService;
import com.r7.core.uim.service.UimResourceService;
import com.r7.core.uim.service.UimUserService;
import com.r7.core.uim.vo.UimChillInfoVO;
import com.r7.core.uim.vo.UimChillVO;
import com.r7.core.uim.vo.UimResourceUrlVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zs
 * @description: 冻结服务实现层
 * @date : 2020-10-13
 */
@Slf4j
@Service
public class UimChillServiceImpl extends ServiceImpl<UimChillMapper, UimChill> implements UimChillService {

    @Resource
    private UimUserService uimUserService;

    @Resource
    private UimResourceService uimResourceService;

    @Resource
    private RedisListService redisListService;

    @PostConstruct
    public void init() {
        List<UimChillInfoVO> uimChillInfoVOS = listChillResource();
        if (uimChillInfoVOS == null) {
            return;
        }
        redisListService.removeByKey(RedisConstant.REDIS_CHILL_RESOURCE_KEY);
        redisListService.addListValue(RedisConstant.REDIS_CHILL_RESOURCE_KEY,
                uimChillInfoVOS, PushType.LEFT);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UimChillVO saveUimChill(UimChillSaveDTO uimChillSaveDto, Long appId, Long organId, Long userId) {
        Long chillUserId = uimChillSaveDto.getUserId();
        Long resourceId = uimChillSaveDto.getResourceId();
        if (chillUserId.toString().length() != 19) {
            throw new BusinessException(UimErrorEnum.CHILL_USER_ID_LENGTH_INCORRECT);
        }
        if (resourceId.toString().length() != 19) {
            throw new BusinessException(UimErrorEnum.CHILL_RESOURCE_ID_LENGTH_INCORRECT);
        }
        log.info("平台:{}对组织:{}中的用户{}资源:{}操作冻结,操作人：{}。", appId, organId, chillUserId, resourceId, userId);
        uimUserService.getUserById(chillUserId);
        uimResourceService.getUimResourceById(resourceId, appId);
        Long id = SnowflakeUtil.getSnowflakeId();
        UimChill uimChill = new UimChill();
        uimChill.setId(id);
        uimChill.toUimChill(uimChillSaveDto);
        uimChill.setCreatedBy(userId);
        uimChill.setCreatedAt(new Date());
        uimChill.setUpdatedBy(userId);
        uimChill.setUpdatedAt(new Date());
        boolean save = save(uimChill);
        if (!save) {
            log.info("平台:{}对组织:{}中的用户{}资源:{}操作冻结失败,操作人：{}。",
                    appId, organId, chillUserId, resourceId, userId);
            throw new BusinessException(UimErrorEnum.CHILL_SAVE_ERROR);
        }
        log.info("平台:{}对组织:{}中的用户{}资源:{}操作冻结成功,操作人：{}。", appId, organId, chillUserId, resourceId, userId);
        return uimChill.toUimChillVo();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean frostUimChill(UimChillSaveListDTO uimChillSaveListDTO, Long appId, Long organId, Long userId) {
        Long chillUserId = uimChillSaveListDTO.getUserId();
        log.info("平台:{}对组织:{}中的用户{}权限进行批量冻结,操作人：{}。", appId, organId, chillUserId, userId);
        Option.of(chillUserId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.CHILL_USER_ID_IS_NULL));
        if (chillUserId.toString().length() != 19) {
            throw new BusinessException(UimErrorEnum.CHILL_USER_ID_LENGTH_INCORRECT);
        }
        List<Long> resourceIds = uimChillSaveListDTO.getResourceIds();
        //解冻全部
        meltingUimChillById(chillUserId, appId, organId, userId);

        String description = uimChillSaveListDTO.getDescription();
        UimChillSaveDTO uimChillSaveDTO = new UimChillSaveDTO();
        uimChillSaveDTO.setUserId(chillUserId);
        uimChillSaveDTO.setDescription(description);
        //再冻结
        for (Long resourceId : resourceIds) {
            log.info("平台:{}对组织:{}中的用户{}权限进行批量冻结,操作人：{}。", appId, organId, chillUserId, userId);
            if (resourceId.toString().length() != 19) {
                throw new BusinessException(UimErrorEnum.CHILL_RESOURCE_ID_LENGTH_INCORRECT);
            }
            uimChillSaveDTO.setResourceId(resourceId);
            saveUimChill(uimChillSaveDTO, appId, organId, userId);
        }
        init();
        log.info("平台:{}对组织:{}中的用户{}权限进行批量冻结操作成功,操作人：{}。", appId, organId, chillUserId, userId);
        return true;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean meltingUimChillById(Long meltingUserId, Long appId, Long organId, Long userId) {
        log.info("平台:{}对组织:{}中的用户:{}进行解冻操作,操作人:{}", appId, organId, meltingUserId, userId);
        Option.of(meltingUserId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.CHILL_USER_ID_IS_NULL));
        if (meltingUserId.toString().length() != 19) {
            throw new BusinessException(UimErrorEnum.CHILL_USER_ID_LENGTH_INCORRECT);
        }

        List<UimChill> uimChillList = list(Wrappers.<UimChill>lambdaQuery()
                .select(UimChill::getId).eq(UimChill::getUserId, meltingUserId));
        if (uimChillList == null || uimChillList.size() == 0) {
            return true;
        }
        //获取到UimChill中存在用户id的全部数据
        List<Long> ids = uimChillList.stream().map(UimChill::getId).collect(Collectors.toList());
        boolean remove = removeByIds(ids);
        if (!remove) {
            log.info("平台:{}对组织:{}中的用户:{}进行解冻失败,操作人:{}", appId, organId, meltingUserId, userId);
            throw new BusinessException(UimErrorEnum.CHILL_ID_IS_NOT_EXISTS);
        }
        log.info("平台:{}对组织:{}中的用户:{}进行解冻成功,操作人:{}", appId, organId, meltingUserId, userId);
        return true;
    }

    @Override
    public List<UimChillVO> listChillByUserId(Long userId) {
        Option.of(userId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.CHILL_USER_ID_IS_NULL));
        String str = userId.toString();
        if (str.length() != 19) {
            throw new BusinessException(UimErrorEnum.CHILL_USER_ID_LENGTH_INCORRECT);
        }
        List<UimChill> uimChillList = list(Wrappers.<UimChill>lambdaQuery()
                .select(UimChill::getId, UimChill::getUserId,
                        UimChill::getResourceId, UimChill::getDescription)
                .in(UimChill::getUserId, userId));
        if (uimChillList == null || uimChillList.size() == 0) {
            return null;
        }
        List<UimChillVO> listUimChillVO = new ArrayList<>();
        uimChillList.forEach(x -> listUimChillVO.add(x.toUimChillVo()));
        return listUimChillVO;
    }

    @Override
    public List<UimChillInfoVO> listChillResource() {
        List<UimChill> uimChillList = list(Wrappers.<UimChill>lambdaQuery()
                .select(UimChill::getUserId,
                        UimChill::getResourceId));
        if (uimChillList == null || uimChillList.size() == 0) {
            return null;
        }
        List<Long> resourceIds = Option.of(uimChillList)
                .filter(x -> x.size() > 0)
                .map(x -> x.stream().map(UimChill::getResourceId).collect(Collectors.toList())).getOrNull();
        List<UimResourceUrlVO> resourceUrlVOList = uimResourceService.listResourceUrlByIds(resourceIds);
        List<UimChillInfoVO> uimChillInfoVOList = Lists.newArrayListWithCapacity(uimChillList.size());

        Map<Long, List<UimChill>> userMap = uimChillList.stream().collect(Collectors.groupingBy(UimChill::getUserId));

        userMap.forEach((k, v) -> {
            UimChillInfoVO uimChillInfoVO = new UimChillInfoVO();
            uimChillInfoVO.setUserId(k);
            List<String> resourceUrls = Lists.newArrayListWithCapacity(v.size());
            v.forEach(x -> {
                resourceUrlVOList.stream().filter(y -> x.getResourceId().equals(y.getId())).forEach(y -> {
                    resourceUrls.add(y.getUrl());
                });
            });
            if (resourceUrls.size() > 0) {
                uimChillInfoVO.setResourceUrl(resourceUrls);
                uimChillInfoVOList.add(uimChillInfoVO);
            }
        });
        return uimChillInfoVOList;
    }

}
