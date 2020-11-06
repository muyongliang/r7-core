package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.dto.UimOauthOrderDTO;
import com.r7.core.uim.dto.UimOauthOrderUpdateDTO;
import com.r7.core.uim.model.UimOauthOrder;
import com.r7.core.uim.vo.UimOauthOrderVO;

/**
 * @author zs
 * @description: 认证订单服务层
 * @date : 2020-10-14
 */
public interface UimOauthOrderService extends IService<UimOauthOrder> {

    /**
     * 创建认证订单
     *
     * @param uimOauthOrderDto 认证订单传输实体
     * @param appId            平台id
     * @param organId          组织id
     * @param userId           操作人id
     * @return 返回订单展示结果
     */
    UimOauthOrderVO saveUimOauthOrder(UimOauthOrderDTO uimOauthOrderDto,
                                      Long appId, Long organId, Long userId);

    /**
     * 修改认证订单
     *
     * @param id                     订单id
     * @param uimOauthOrderUpdateDto 认证订单修改传输实体
     * @param appId                  平台id
     * @param organId                组织id
     * @param userId                 操作人id
     * @return 返回修改结果
     */
    UimOauthOrderVO updateUimOauthOrder(Long id, UimOauthOrderUpdateDTO uimOauthOrderUpdateDto,
                                        Long appId, Long organId, Long userId);

    /**
     * 根据id查询认证订单信息
     *
     * @param id 认证订单id
     * @return 返回查询结果
     */
    UimOauthOrderVO getUimOauthOrderById(Long id);
}
