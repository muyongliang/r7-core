package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.dto.UimOauthDTO;
import com.r7.core.uim.mapper.UimOauthServiceMapper;
import com.r7.core.uim.model.UimOauth;
import com.r7.core.uim.service.UimOauthOrderService;
import com.r7.core.uim.service.UimOauthService;
import com.r7.core.uim.service.UimUserService;
import com.r7.core.uim.vo.UimOauthVO;
import com.r7.core.uim.vo.UimUserVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zs
 * @description: 认证服务实现
 * @date : 2020-10-14
 */
@Slf4j
@Service
public class UimOauthServiceImpl extends ServiceImpl<UimOauthServiceMapper, UimOauth> implements UimOauthService {

    @Resource
    private UimUserService uimUserService;

    @Resource
    private UimOauthOrderService uimOauthOrderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUimOauth(UimOauthDTO uimOauthDto, Long appId, Long organId, Long userId) {
        Long oauthUserId = uimOauthDto.getUserId();
        Long oauthOrderId = uimOauthDto.getOauthOrderId();
        log.info("平台:{}中的组织:{}用户:{}新增认证，操作人:{}。", appId, organId, oauthUserId, userId);
        Option.of(oauthUserId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.OAUTH_ORDER_USER_ID_IS_NULL));
        Option.of(oauthOrderId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.OAUTH_ORDER_ID_IS_NULL));
        String str = oauthUserId.toString();
        int standard = 19;
        if (str.length() != standard) {
            throw new BusinessException(UimErrorEnum.OAUTH_SERVICE_USER_ID_LENGTH_INCORRECT);
        }
        Option.of(uimUserService.getUserById(oauthUserId)).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NOT_EXIST));
        if (oauthOrderId.toString().length() != standard) {
            throw new BusinessException(UimErrorEnum.OAUTH_SERVICE_USER_ID_LENGTH_INCORRECT);
        }

        Option.of(uimOauthOrderService.getUimOauthOrderById(oauthOrderId)).getOrElseThrow(() -> new BusinessException(UimErrorEnum.OAUTH_ORDER_IS_NOT_EXISTS));
        Long id = SnowflakeUtil.getSnowflakeId();
        UimOauth uimOauth = new UimOauth();
        uimOauth.setId(id);
        uimOauth.setAppId(appId);
        uimOauth.toUimOauth(uimOauthDto);
        uimOauth.setCreatedBy(userId);
        uimOauth.setCreatedAt(new Date());
        uimOauth.setUpdatedBy(userId);
        uimOauth.setUpdatedAt(new Date());
        boolean save = save(uimOauth);
        if (!save) {
            log.info("平台:{}中的组织:{}用户:{}新增认证失败，操作人:{}。", appId, organId, oauthUserId, userId);
            throw new BusinessException(UimErrorEnum.OAUTH_SAVE_ERROR);
        }
        log.info("平台:{}中的组织:{}用户:{}新增认证成功，操作人:{}。", appId, organId, oauthUserId, userId);
        return true;
    }

    @Override
    public List<UimOauthVO> listUimOauth(Long userId) {
        Option.of(userId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.OAUTH_USER_ID_IS_NULL));
        String str = userId.toString();
        int standard = 19;
        if (str.length() != standard) {
            throw new BusinessException(UimErrorEnum.OAUTH_SERVICE_USER_ID_LENGTH_INCORRECT);
        }
        Option.of(uimUserService.getUserById(userId)).getOrElseThrow(() -> new BusinessException(UimErrorEnum.OAUTH_USER_ID_IS_Not_EXISTS));
        List<UimOauth> uimOauthList = list(Wrappers.<UimOauth>lambdaQuery()
                .select(UimOauth::getId, UimOauth::getUserId,
                        UimOauth::getAppId, UimOauth::getOauthOrderId,
                        UimOauth::getType, UimOauth::getStatus,
                        UimOauth::getReason)
                .in(UimOauth::getUserId, userId));
        if (uimOauthList == null || uimOauthList.size() == 0) {
            return null;
        }
        ArrayList<UimOauthVO> listUimOauthVo = new ArrayList<>();
        uimOauthList.forEach(x -> listUimOauthVo.add(x.toUimOauthVo()));
        return listUimOauthVo;
    }
}
