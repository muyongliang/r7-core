package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.dto.UimChillSaveDTO;
import com.r7.core.uim.dto.UimChillSaveListDTO;
import com.r7.core.uim.mapper.UimChillMapper;
import com.r7.core.uim.model.UimChill;
import com.r7.core.uim.service.UimChillService;
import com.r7.core.uim.service.UimResourceService;
import com.r7.core.uim.service.UimUserService;
import com.r7.core.uim.vo.UimChillVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Override
    @Transactional
    public UimChillVO saveUimChill(UimChillSaveDTO uimChillSaveDto, Long appId, Long organId, Long userId) {
        Long chillUserId = uimChillSaveDto.getUserId();
        Long resourceId = uimChillSaveDto.getResourceId();
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
    @Transactional
    public Boolean frostUimChill(UimChillSaveListDTO uimChillSaveListDTO, Long appId, Long organId, Long userId) {
        Long chillUserId = uimChillSaveListDTO.getUserId();
        List<Long> resourceIds = uimChillSaveListDTO.getResourceIds();

        //解冻全部
        meltingUimChill(chillUserId, appId, organId, userId);

        String description = uimChillSaveListDTO.getDescription();
        UimChillSaveDTO uimChillSaveDTO = new UimChillSaveDTO();
        uimChillSaveDTO.setUserId(chillUserId);
        uimChillSaveDTO.setDescription(description);
        //再冻结
        for (Long resourceId : resourceIds) {
            uimChillSaveDTO.setResourceId(resourceId);
            saveUimChill(uimChillSaveDTO, appId, organId, userId);
        }
        return true;
    }


    @Override
    @Transactional
    public Boolean meltingUimChill(Long meltingUserId, Long appId, Long organId, Long userId) {
        log.info("平台:{}对组织:{}中的用户:{}进行解冻操作,操作人:{}", appId, organId, meltingUserId, userId);
        List<UimChill> uimChillList = list(Wrappers.<UimChill>lambdaQuery().eq(UimChill::getUserId, meltingUserId));
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

}
