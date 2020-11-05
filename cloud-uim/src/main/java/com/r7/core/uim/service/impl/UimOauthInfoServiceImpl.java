package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.dto.UimOauthInfoDTO;
import com.r7.core.uim.mapper.UimOauthInfoMapper;
import com.r7.core.uim.model.UimOauthInfo;
import com.r7.core.uim.service.UimOauthInfoService;
import com.r7.core.uim.service.UimUserService;
import com.r7.core.uim.vo.UimOauthInfoVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zs
 * @description: 认证信息服务实现层
 * @date : 2020-10-14
 */
@Slf4j
@Service
public class UimOauthInfoServiceImpl
        extends ServiceImpl<UimOauthInfoMapper, UimOauthInfo> implements UimOauthInfoService {

    @Resource
    private UimUserService uimUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUimOauthInfo(Long userId, UimOauthInfoDTO uimOauthInfoDto, Long appId, Long organId) {
        log.info("平台:{}下的组织:{}用户:{}认证信息保存，操作人id:{}。", appId, organId, userId, userId);
        Option.of(userId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NULL));
        int standard = 19;
        if (userId.toString().length() != standard) {
            throw new BusinessException(UimErrorEnum.OAUTH_INFO_USER_ID_LENGTH_INCORRECT);
        }
        Option.of(uimUserService.getUserById(userId))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NOT_EXIST));
        Long id = SnowflakeUtil.getSnowflakeId();
        UimOauthInfo uimOauthInfo = new UimOauthInfo();
        uimOauthInfo.setId(id);
        uimOauthInfo.setUserId(userId);
        uimOauthInfo.toOauthInfo(uimOauthInfoDto);
        uimOauthInfo.setCreatedBy(userId);
        uimOauthInfo.setCreatedAt(new Date());
        uimOauthInfo.setUpdatedBy(userId);
        uimOauthInfo.setUpdatedAt(new Date());
        boolean save = save(uimOauthInfo);
        if (!save) {
            log.info("平台:{}下的组织:{}用户:{}认证信息保存失败，操作人id:{}。", appId, organId, userId, userId);
            throw new BusinessException(UimErrorEnum.OAUTH_INFO_SAVE_ERROR);
        }
        log.info("平台:{}下的组织:{}用户:{}认证信息保存成功，操作人id:{}。", appId, organId, userId, userId);
        return true;
    }

    @Override
    public UimOauthInfoVO getUimOauthInfoByUserId(Long userId) {
        Option.of(userId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NULL));
        int standard = 19;
        if (userId.toString().length() != standard) {
            throw new BusinessException(UimErrorEnum.OAUTH_INFO_USER_ID_LENGTH_INCORRECT);
        }
        UimOauthInfo uimOauthInfo = Option.of(getOne(Wrappers.<UimOauthInfo>lambdaQuery()
                .select(UimOauthInfo::getId, UimOauthInfo::getUserId, UimOauthInfo::getOauthName,
                        UimOauthInfo::getIdentity, UimOauthInfo::getSex, UimOauthInfo::getAge,
                        UimOauthInfo::getBirthday, UimOauthInfo::getNativePlace, UimOauthInfo::getProvince,
                        UimOauthInfo::getCity, UimOauthInfo::getCounty)
                .eq(UimOauthInfo::getUserId, userId)))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NOT_EXIST));
        return uimOauthInfo.toUimOauthInfoVo();
    }

}
