package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.uim.mapper.UimUserMapper;
import com.r7.core.uim.model.UimUser;
import com.r7.core.uim.service.UimSysUserService;
import com.r7.core.uim.service.UimUserService;
import com.r7.core.uim.vo.UimUserDetailsVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务实现层
 *
 * @author zhongpingli
 */
@Slf4j
@Service
public class UimUserServiceImpl extends ServiceImpl<UimUserMapper, UimUser> implements UimUserService, UserDetailsService {

    @Resource
    private UimSysUserService uimSysUserService;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        // 普通用户使用电话号 // 系统用户登录账号
        UimUserDetailsVO uimUserDetailsVO = Option.of(baseMapper.findUserDetailsByLogin(loginName))
                .getOrElse(uimSysUserService.findUserDetailsByLogin(loginName));
        Option.of(uimUserDetailsVO).getOrElseThrow(() -> new BusinessException("", ""));


        return null;
    }
}
