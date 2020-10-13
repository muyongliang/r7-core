package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.dto.UimUserUpdateDTO;
import com.r7.core.uim.dto.UserSingUpDTO;
import com.r7.core.uim.mapper.UimUserMapper;
import com.r7.core.uim.model.UimUser;
import com.r7.core.uim.service.UimSysUserService;
import com.r7.core.uim.service.UimUserRoleService;
import com.r7.core.uim.service.UimUserService;
import com.r7.core.uim.vo.UimUserDetailsVO;
import com.r7.core.uim.vo.UimUserVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UimUserRoleService uimUserRoleService;

    private static Hashids hashids = new Hashids("uim_user");

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        // 普通用户使用电话号 & 系统用户登录账号
        UimUserDetailsVO uimUserDetailsVO = Option.of(baseMapper.findUserDetailsByLogin(loginName))
                .getOrElse(uimSysUserService.findUserDetailsByLogin(loginName));
        Option.of(uimUserDetailsVO).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_LOGIN_NAME_ERROR));

        // 添加角色
        List<String> listRoleCode = uimUserRoleService.listRoleCode(uimUserDetailsVO.getId(), 0L);
        uimUserDetailsVO.setRoles(listRoleCode);
        return uimUserDetailsVO;
    }

    @Override
    @Transactional
    public UimUserVO singUpUser(String code, UserSingUpDTO userSingUpDTO, String ip) {
        log.info("新用户注册：{},IP地址{}", userSingUpDTO, ip);
        // 电话号是否已经存在
        UimUserVO userByPhone = getUserByPhone(userSingUpDTO.getPhoneNumber());
        Option.of(userByPhone).exists(eror -> {
            throw new BusinessException(UimErrorEnum.USER_PHONE_EXISTS);
        });
        Long id = SnowflakeUtil.getSnowflakeId();
        UimUser uimUser = new UimUser();
        uimUser.setId(id);
        uimUser.toUserSingUpDTO(userSingUpDTO);
        uimUser.setPassword(passwordEncoder.encode(userSingUpDTO.getPassword()));
        uimUser.setCode(hashids.encode(id));
        uimUser.setAvatar("abc");
        uimUser.setIp(ip);
        // 未认证
        uimUser.setIsOauth(2);
        // 正常
        uimUser.setStatus(1);
        // 未注销
        uimUser.setDel(2);
        uimUser.setCreatedAt(new Date());
        uimUser.setCreatedBy(id);
        uimUser.setUpdatedAt(new Date());
        uimUser.setUpdatedBy(id);

        boolean save = save(uimUser);
        // todo 层级
        if (!save) {
            log.info("新用户注册：{}失败,IP地址{}", userSingUpDTO, ip);
            throw new BusinessException(UimErrorEnum.USER_SAVE_ERROR);
        }
        return getUserById(id);
    }

    @Override
    @Transactional
    public UimUserVO updateUser(Long id, UimUserUpdateDTO uimUserUpdateDTO, Long userId) {
        log.info("用户修改信息：{},操作人：{}", uimUserUpdateDTO, userId);
        Option.of(id).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_ID_IS_NULL));
        UimUser uimUser = Option.of(getById(id))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_IS_NOT_EXISTS));
        uimUser.toUimUserUpdateDTO(uimUserUpdateDTO);
        uimUser.setUpdatedBy(userId);
        uimUser.setUpdatedAt(new Date());
        boolean update = updateById(uimUser);
        if (!update) {
            log.info("用户修改信息：{}失败,操作人：{}", uimUser, userId);
            throw new BusinessException(UimErrorEnum.USER_UPDATE_ERROR);
        }
        log.info("用户修改信息：{}成功,操作人：{}", uimUser, userId);
        return getUserById(id);
    }

    @Override
    public UimUserVO getUserById(Long id) {
        UimUser uimUser = getOne(Wrappers.<UimUser>lambdaQuery()
                .select(UimUser::getId, UimUser::getAvatar,
                        UimUser::getUserName, UimUser::getCode, UimUser::getPhoneNumber)
                .eq(UimUser::getId, id));
        return Option.of(uimUser).map(UimUser::toUimUserVO).getOrNull();
    }

    @Override
    public IPage<UimUserVO> pageUser(String search, Long organId, int pageNum, int pageSize) {
        Page<UimUserVO> page = new Page<>(pageNum, pageSize);
        return baseMapper.pageUser(search, organId, page);
    }

    @Override
    public IPage<UimUserVO> pageUser(String search, int pageNum, int pageSize) {
        Page<UimUserVO> page = new Page<>(pageNum, pageSize);
        return baseMapper.pageUser(search, null, page);
    }

    @Override
    public UimUserVO getUserByPhone(String phone) {
        UimUser uimUser = getOne(Wrappers.<UimUser>lambdaQuery()
                .select(UimUser::getId, UimUser::getAvatar,
                        UimUser::getUserName, UimUser::getCode, UimUser::getPhoneNumber)
                .eq(UimUser::getPhoneNumber, phone));
        return Option.of(uimUser).map(UimUser::toUimUserVO).getOrNull();
    }
}
