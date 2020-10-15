package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.dto.UimOauthInfoDTO;
import com.r7.core.uim.model.UimOauthInfo;
import com.r7.core.uim.vo.UimOauthInfoVO;

/**
 * @author zs
 * @description: 认证信息服务层
 * @date : 2020-10-14
 */
public interface UimOauthInfoService extends IService<UimOauthInfo> {

    /**
     * 新增认证信息
     * @param uimOauthInfoDto 用户认证信息传输对象
     * @param appId           平台id
     * @param organId         组织id
     * @param userId          操作人id
     * @return 返回是否成功
     */
    Boolean saveUimOauthInfo(Long userId, UimOauthInfoDTO uimOauthInfoDto, Long appId, Long organId);

    /**
     * 根据用户id查询用户认证信息
     * @param userId 用户id
     * @return 返回认证信息结果
     */
    UimOauthInfoVO getUimOauthInfoByUserId(Long userId);
}
