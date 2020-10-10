package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.uim.mapper.UimSysUserMapper;
import com.r7.core.uim.model.UimSysUser;
import com.r7.core.uim.service.UimSysUserService;
import com.r7.core.uim.vo.UimUserDetailsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 系统用户服务实现层
 *
 * @author zhongpingli
 */
@Slf4j
@Service
public class UimSysUserServiceImpl extends ServiceImpl<UimSysUserMapper, UimSysUser> implements UimSysUserService {

    @Override
    public UimUserDetailsVO findUserDetailsByLogin(String loginName) {



        return null;
    }
}
