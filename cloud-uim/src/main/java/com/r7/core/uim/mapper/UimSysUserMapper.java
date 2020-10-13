package com.r7.core.uim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.r7.core.uim.model.UimSysUser;
import com.r7.core.uim.vo.UimUserDetailsVO;

/**
 * 系统用户mapper层
 *
 * @author zhongpingli
 */
public interface UimSysUserMapper extends BaseMapper<UimSysUser> {

    /**
     * 根据id获取用户信息
     *
     * @param login 登陆账户
     * @return
     */
    UimUserDetailsVO findUserDetailsByLogin(String login);

}
