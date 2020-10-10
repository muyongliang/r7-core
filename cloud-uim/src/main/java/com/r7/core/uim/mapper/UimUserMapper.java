package com.r7.core.uim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.r7.core.uim.model.UimUser;
import com.r7.core.uim.vo.UimUserDetailsVO;

/**
 * 用户mapper
 *
 * @author zhongpingli
 */
public interface UimUserMapper extends BaseMapper<UimUser> {

    /**
     * 根据id获取用户信息
     *
     * @param login 电话号
     * @return
     */
    UimUserDetailsVO findUserDetailsByLogin(String login);

}
