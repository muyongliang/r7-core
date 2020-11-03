package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.dto.UimOauthOrderDTO;
import com.r7.core.uim.mapper.UimOauthOrderMapper;
import com.r7.core.uim.model.UimOauthOrder;
import com.r7.core.uim.service.UimOauthOrderService;
import com.r7.core.uim.vo.UimOauthOrderVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zs
 * @description: 认证订单服务实现层
 * @date : 2020-10-14
 */
@Slf4j
@Service
public class UimOauthOrderServiceImpl
        extends ServiceImpl<UimOauthOrderMapper, UimOauthOrder> implements UimOauthOrderService {

    @Override
    public UimOauthOrderVO saveUimOauthOrder(UimOauthOrderDTO uimOauthOrderDto,
                                             Long appId, Long organId, Long userId) {
        Long oauthUserId = uimOauthOrderDto.getUserId();
        log.info("平台:{}中的组织:{}用户:{}创建认证订单，操作人:{}。", appId, organId, oauthUserId, userId);
        Option.of(oauthUserId).getOrElseThrow(() -> new BusinessException(UimErrorEnum.OAUTH_USER_ID_IS_Not_EXISTS));
        Long id = SnowflakeUtil.getSnowflakeId();
        UimOauthOrder uimOauthOrder = new UimOauthOrder();
        uimOauthOrder.setId(id);
        uimOauthOrder.setAppId(appId);
        uimOauthOrder.toUimOauthOrder(uimOauthOrderDto);
        uimOauthOrder.setCreatedBy(userId);
        uimOauthOrder.setCreatedAt(new Date());
        uimOauthOrder.setUpdatedBy(userId);
        uimOauthOrder.setUpdatedAt(new Date());

        boolean save = save(uimOauthOrder);
        if (!save) {
            log.info("平台:{}中的组织:{}用户:{}创建认证订单失败，操作人:{}。", appId, organId, oauthUserId, userId);
            throw new BusinessException(UimErrorEnum.OAUTH_ORDER_SAVE_ERROR);
        }
        log.info("平台:{}中的组织:{}用户:{}创建认证订单成功，操作人:{}。", appId, organId, oauthUserId, userId);
        return uimOauthOrder.toUimOauthOrderVo();
    }

    @Override
    public UimOauthOrderVO updateUimOauthOrder(Long id, UimOauthOrderDTO uimOauthOrderDto,
                                               Long appId, Long organId, Long userId) {
        Long oauthUserId = uimOauthOrderDto.getUserId();
        log.info("平台:{}中的组织:{}用户:{}修改认证订单，操作人:{}。", appId, organId, oauthUserId, userId);
        id = Option.of(id).getOrElseThrow(() -> new BusinessException(UimErrorEnum.OAUTH_ORDER_ID_IS_NULL));
        UimOauthOrder uimOauthOrder = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.OAUTH_ORDER_IS_NOT_EXISTS));
        uimOauthOrder.toUimOauthOrder(uimOauthOrderDto);
        uimOauthOrder.setUpdatedBy(userId);
        uimOauthOrder.setUpdatedAt(new Date());
        boolean update = updateById(uimOauthOrder);
        if (!update) {
            log.info("平台:{}中的组织:{}用户:{}修改认证订单失败，操作人:{}。", appId, organId, oauthUserId, userId);
            throw new BusinessException(UimErrorEnum.OAUTH_ORDER_UPDATE_ERROR);
        }
        log.info("平台:{}中的组织:{}用户:{}修改认证订单成功，操作人:{}。", appId, organId, oauthUserId, userId);
        return uimOauthOrder.toUimOauthOrderVo();
    }

    @Override
    public UimOauthOrderVO getUimOauthOrderById(Long id) {
        id = Option.of(id).getOrElseThrow(() -> new BusinessException(UimErrorEnum.OAUTH_ORDER_ID_IS_NULL));
        UimOauthOrder uimOauthOrder = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.OAUTH_ORDER_IS_NOT_EXISTS));
        return uimOauthOrder.toUimOauthOrderVo();
    }
}
