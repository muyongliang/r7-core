package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.dto.UimChillSaveDTO;
import com.r7.core.uim.dto.UimChillUpdateDTO;
import com.r7.core.uim.mapper.UimChillMapper;
import com.r7.core.uim.model.UimChill;
import com.r7.core.uim.service.UimChillService;
import com.r7.core.uim.service.UimResourceService;
import com.r7.core.uim.service.UimUserService;
import com.r7.core.uim.vo.UimChillVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

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
    public UimChillVO updateUimChill(Long id, UimChillUpdateDTO uimChillUpdateDTO,
                                     Long appId, Long organId, Long userId) {
        Long resourceId = uimChillUpdateDTO.getResourceId();
        String description = uimChillUpdateDTO.getDescription();
        log.info("平台:{}对组织:{}中的冻结资源:{}操作修改,操作人：{}。", appId, organId, resourceId, userId);
        UimChill uimChill = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.CHILL_ID_IS_NOT_EXISTS));
        uimChill.setResourceId(resourceId);
        uimChill.setDescription(description);
        uimChill.setUpdatedBy(userId);
        uimChill.setUpdatedAt(new Date());
        boolean update = updateById(uimChill);
        if (!update) {
            log.info("平台:{}对组织:{}中的冻结资源:{}操作修改,操作人：{}。", appId, organId, resourceId, userId);
            throw new BusinessException(UimErrorEnum.CHILL_UPDATE_ERROR);
        }
        log.info("平台:{}对组织:{}中的冻结资源:{}修改成功,操作人：{}。", appId, organId, resourceId, userId);
        return uimChill.toUimChillVo();
    }

    @Override
    public UimChillVO getChillById(Long id) {
        UimChill uimChill = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.CHILL_ID_IS_NOT_EXISTS));
        return uimChill.toUimChillVo();
    }

    @Override
    public UimChillVO getChillByUserId(Long userId) {
        UimChill uimChill = Option.of(getOne(Wrappers.<UimChill>lambdaQuery().eq(UimChill::getUserId, userId)))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.CHILL_USER_ID_IS_NOT_EXISTS));
        return uimChill.toUimChillVo();
    }

}
