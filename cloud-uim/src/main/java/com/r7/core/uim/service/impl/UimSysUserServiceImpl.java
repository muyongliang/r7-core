package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.common.util.ValidatorUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.constant.UimSysUserEnum;
import com.r7.core.uim.dto.UimSysUserDTO;
import com.r7.core.uim.dto.UimSysUserUpdateDTO;
import com.r7.core.uim.mapper.UimSysUserMapper;
import com.r7.core.uim.model.UimSysUser;
import com.r7.core.uim.service.UimSysUserService;
import com.r7.core.uim.vo.UimResourceVO;
import com.r7.core.uim.vo.UimRoleVO;
import com.r7.core.uim.vo.UimSysUserVO;
import com.r7.core.uim.vo.UimUserDetailsVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 系统用户服务实现层
 *
 * @author zhongpingli
 */
@Slf4j
@Service
public class UimSysUserServiceImpl extends ServiceImpl<UimSysUserMapper, UimSysUser> implements UimSysUserService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UimSysUserVO saveUimSysUser(UimSysUserDTO uimSysUserDTO, String ip
            ,Long appId,Long organId,Long userId) {
        log.info("系统新增用户信息：{}，新增用户ID：{}，平台ID：{}，组织ID：{}，操作者ID：{},新增开始时间：{}",
                 uimSysUserDTO, ip, appId, organId, userId, LocalDateTime.now());

        //验证手机格式
        if (!ValidatorUtil.validatorPhoneNumber(Long.valueOf(uimSysUserDTO.getPhoneNumber()))){
                throw new BusinessException(UimErrorEnum.USER_PHONE_ERROR);
        }

        //验证邮箱格式
        if ( !ValidatorUtil.validatorEmail(uimSysUserDTO.getEmail())) {
            throw new BusinessException(UimSysUserEnum.USER_EMAIL_ERROR);
        }
        //验证手机号是否存在
        getUimSysUserPhoneNumber(uimSysUserDTO.getPhoneNumber());
        //验证邮箱是否存在
        getUimSysUserEmail(uimSysUserDTO.getEmail());
        // todo 平台需要验证是否存在？
        //验证用户名是否存在
        getUimSysUserByUserName(uimSysUserDTO.getUserName());
        //添加
        UimSysUser uimSysUser = new UimSysUser();
        uimSysUser.setId(SnowflakeUtil.getSnowflakeId());
        uimSysUser.toUimSysUserDTO(uimSysUserDTO);
        uimSysUser.setIp(ip);
        uimSysUser.setAppId(appId);
        uimSysUser.setOrganId(organId);
        uimSysUser.setCreatedAt(new Date());
        uimSysUser.setUpdatedAt(new Date());
        uimSysUser.setCreatedBy(userId);
        uimSysUser.setUpdatedBy(userId);

       boolean saveUimSysUser = save(uimSysUser);
        if (!saveUimSysUser ) {
            log.info("系统新增用户信息失败：{}，新增用户ID：{}，平台ID：{}，组织ID：{}，部门ID：{}" +
                            "，操作者ID：{},新增失败时间：{}",
                    uimSysUserDTO, ip, appId, organId, userId, LocalDateTime.now());
            throw new BusinessException(UimSysUserEnum.USER_SYS_SAVE_ERROR);
        }

        log.info("系统新增用户信息：{}，新增用户ID：{}，平台ID：{}，组织ID：{}，部门ID：{}，操作者ID：{},新增开始时间：{}",
                uimSysUserDTO, ip, appId, organId,  userId, LocalDateTime.now());
        return uimSysUser.toUimSysUserVO();
    }

    @Override
    public UimSysUserVO getUimSysUserPhoneNumber(String phoneNumber) {
        log.info("电话号码：{}，开始时间：{}",phoneNumber,LocalDateTime.now());



        UimSysUser uimSysUser =   Option.of(getOne(Wrappers.<UimSysUser>lambdaQuery().select(
                UimSysUser::getId,
                UimSysUser::getOrganId,
                UimSysUser::getAppId,
                UimSysUser::getAvatar,
                UimSysUser::getBranchId,
                UimSysUser::getLoginName,
                UimSysUser::getPhoneNumber,
                UimSysUser::getStatus,
                UimSysUser::getPassword,
                UimSysUser::getUserName
        ).eq(UimSysUser::getPhoneNumber,phoneNumber)))
                .getOrElseThrow(()->new BusinessException(UimSysUserEnum.USER_SYS_PHONE_IS_NOT_EXISTS));

        log.info("电话号码：{}，结束时间：{}",phoneNumber,LocalDateTime.now());
        return uimSysUser.toUimSysUserVO();
    }

    @Override
    public UimSysUserVO getUimSysUserEmail(String email) {
        log.info("邮箱：{}，开始时间：{}",email,LocalDateTime.now());
        UimSysUser uimSysUser =   Option.of(getOne(Wrappers.<UimSysUser>lambdaQuery().select(
                UimSysUser::getId,
                UimSysUser::getAppId,
                UimSysUser::getAvatar,
                UimSysUser::getBranchId,
                UimSysUser::getOrganId,
                UimSysUser::getLoginName,
                UimSysUser::getPhoneNumber,
                UimSysUser::getStatus,
                UimSysUser::getPassword,
                UimSysUser::getUserName
        ).eq(UimSysUser::getEmail,email)))
                .getOrElseThrow(()->new BusinessException(UimSysUserEnum.USER_SYS_EMAIL_IS_NOT_EXISTS));
        log.info("邮箱：{}，结束时间：{}",email,LocalDateTime.now());
        return uimSysUser.toUimSysUserVO();
    }

    @Override
    public UimSysUserVO getUimSysUserByUserName(String userName) {


        return baseMapper.selectOne(Wrappers.<UimSysUser>lambdaQuery()
                .eq(UimSysUser::getUserName,userName)).toUimSysUserVO();

    }

    @Override
    public UimSysUserVO getUimSysUserById(Long id) {
        return null;
    }

    @Override
    public UimRoleVO getUimRoleByUserId(Long id) {
        return null;
    }

    @Override
    public UimResourceVO getUimResourceByUimSysUserId(Long id) {
        return null;
    }

    @Override
    public UimSysUserVO updateUimSysUserById(Long id, UimSysUserUpdateDTO uimSysUserUpdateDTO, Long userId) {
        return null;
    }

    @Override
    public UimUserDetailsVO findUserDetailsByLogin(String loginName) {
        return baseMapper.findUserDetailsByLogin(loginName);
    }
}
