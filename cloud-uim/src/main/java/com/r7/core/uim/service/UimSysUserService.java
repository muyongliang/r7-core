package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.model.UimSysUser;
import com.r7.core.uim.vo.UimUserDetailsVO;

/**
 * 系统用户服务
 *
 * @author zhongpingli
 */
public interface UimSysUserService extends IService<UimSysUser> {

    // 根据名字获取用户信息

    // 根据用户ID获取用户信息

    // 根据用户ID获取角色

    // 根据用户ID获取资源


    /**
     * 根据登录名获取登录信息
     *
     * @param loginName 登录名
     * @return 返回登录信息
     */
    UimUserDetailsVO findUserDetailsByLogin(String loginName);

}
