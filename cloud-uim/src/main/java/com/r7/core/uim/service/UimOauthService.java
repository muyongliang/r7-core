package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.dto.UimOauthDTO;
import com.r7.core.uim.model.UimOauth;
import com.r7.core.uim.vo.UimOauthVO;

import java.util.List;

/**
 * @author zs
 * @description: 认证服务层
 * @date : 2020-10-14
 */
public interface UimOauthService extends IService<UimOauth> {

    /**
     * 新增用户认证
     * @param uimOauthDto 用户认证传输实体
     * @param appId       平台id
     * @param organId     组织id
     * @param userId      操作人id
     * @return 返回是否成功
     */
    Boolean saveUimOauth(UimOauthDTO uimOauthDto, Long appId, Long organId, Long userId);

    /**
     * 根据用户id查询全部认证信息
     * @param userId 用户id
     * @return 返回查询结果
     */
    List<UimOauthVO> listUimOauth(Long userId);
}
