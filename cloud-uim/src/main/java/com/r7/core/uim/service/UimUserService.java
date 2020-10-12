package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.model.UimUser;
import com.r7.core.uim.vo.UimUserVO;

/**
 * @author zs
 * @description: 用户服务层
 * @date : 2020-10-12
 */
public interface UimUserService extends IService<UimUser> {

    /**
     * 根据id获取用户详情
     * @param userId  用户ID
     * @param appId   平台ID
     * @param organId 组织ID
     * @return 返回结果
     */
    UimUserVO getUserById(Long userId, Long appId, Long organId);
}
