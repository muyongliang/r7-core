package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.cache.service.*;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.CodeUtil;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.common.util.ValidatorUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.constant.UimSysUserEnum;
import com.r7.core.uim.dto.UimSysUserDTO;
import com.r7.core.uim.dto.UimSysUserUpdateDTO;
import com.r7.core.uim.mapper.UimSysUserMapper;
import com.r7.core.uim.model.UimSysUser;
import com.r7.core.uim.service.UimSysUserService;
import com.r7.core.uim.service.UimUserService;
import com.r7.core.uim.vo.UimResourceVO;
import com.r7.core.uim.vo.UimRoleVO;
import com.r7.core.uim.vo.UimSysUserVO;
import com.r7.core.uim.vo.UimUserDetailsVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 系统用户服务实现层
 *
 * @author zhongpingli
 */
@Slf4j
@Service
public class UimSysUserServiceImpl extends ServiceImpl<UimSysUserMapper, UimSysUser> implements UimSysUserService {

    @Resource
    private UimUserService uimUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UimSysUserVO saveUimSysUser(String code,UimSysUserDTO uimSysUserDTO, String ip
            ,Long appId,Long organId,Long userId) {
        log.info("系统新增用户信息：{}，新增用户IP：{}，平台ID：{}，组织ID：{}，操作者ID：{},新增开始时间：{}",
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
        if ( Optional.ofNullable(getUimSysUserPhoneNumber(uimSysUserDTO.getPhoneNumber()))
                .isPresent()){
            throw new BusinessException(UimSysUserEnum.USER_SYS_PHONE_IS_EXISTS);
        }

        //验证邮箱是否存在
        if (Optional.ofNullable(getUimSysUserEmail(uimSysUserDTO.getEmail()))
                .isPresent()){
            throw new BusinessException(UimSysUserEnum.USER_SYS_EMAIL_IS_EXISTS);
        }

        //验证昵称是否存在
        if ( Optional.ofNullable(getUimSysUserByUserName(uimSysUserDTO.getUserName()))
                .isPresent()){
            throw new BusinessException(UimSysUserEnum.USER_SYS_USER_NAME_IS_EXISTS);
        }

        //验证登录用户名是否存在
        if (Optional.ofNullable(findUserDetailsByLogin(uimSysUserDTO.getLoginName()))
                .isPresent()) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_LOGIN_NAME_IS_EXISTS);
        }
        //生成id
        Long id = SnowflakeUtil.getSnowflakeId();

        //验证邀请码是否存在
        if (!Optional.ofNullable(getUimSysUserCode(code)).isPresent()
                && !Optional.ofNullable(uimUserService.getUserByCode(code)).isPresent()) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_CODE_IS_NOT_EXISTS);
        }



        //生成邀请码
        String userCode = CodeUtil.instance().gen(id);
        //验证邀请码生成是否重复
        if (Optional.ofNullable(getUimSysUserCode(userCode)).isPresent()) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_CODE_IS_EXISTS);
        }

        Option.of(uimUserService.getUserByCode(userCode)).exists(x -> {
            throw new BusinessException(UimErrorEnum.USER_CODE_IS_EXISTS);
        });
        //添加
        UimSysUser uimSysUser = new UimSysUser();
        uimSysUser.setId(id);
        uimSysUser.setCode(userCode);
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
            log.info("系统新增用户信息失败：{}，新增用户IP：{}，平台ID：{}，组织ID：{}，操作者ID：{},新增失败时间：{}",
                    uimSysUserDTO, ip, appId, organId, userId, LocalDateTime.now());
            throw new BusinessException(UimSysUserEnum.USER_SYS_SAVE_ERROR);
        }

        log.info("系统新增用户信息：{}，新增用户IP：{}，平台ID：{}，组织ID：{}，操作者ID：{},新增结束时间：{}",
                uimSysUserDTO, ip, appId, organId,  userId, LocalDateTime.now());

        return uimSysUser.toUimSysUserVO();
    }

    @Override
    public UimSysUserVO getUimSysUserPhoneNumber(String phoneNumber) {

        Option.of(phoneNumber)
                .getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_PHONE_NUMBER_IS_NOT_NULL));

        UimSysUser uimSysUser =    baseMapper.selectOne(Wrappers.<UimSysUser>lambdaQuery()
                .eq(UimSysUser::getPhoneNumber,phoneNumber));
        if (uimSysUser == null) {
            return null;
        }
        return uimSysUser.toUimSysUserVO();
    }

    @Override
    public UimSysUserVO getUimSysUserEmail(String email) {
        Option.of(email)
                .getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_EMAIL_ID_NOT_NULL));
        UimSysUser uimSysUser = baseMapper.selectOne(Wrappers.<UimSysUser>lambdaQuery()
                .eq(UimSysUser::getEmail,email));
        if (uimSysUser == null) {
            return null;
        }
        return uimSysUser.toUimSysUserVO();
    }

    @Override
    public UimSysUserVO getUimSysUserByUserName(String userName) {
        Option.of(userName).getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_USER_NAME_IS_NOT_NULL));
        UimSysUser uimSysUser = baseMapper.selectOne(Wrappers.<UimSysUser>lambdaQuery()
                .eq(UimSysUser::getUserName,userName));
        if (uimSysUser == null) {
            return null;
        }
        return uimSysUser.toUimSysUserVO();

    }

    @Override
    public UimSysUserVO getUimSysUserById(Long id) {
        Option.of(id).getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_ID_IS_NOT_NULL));
        return baseMapper.selectById(id).toUimSysUserVO();
    }

    @Override
    public List<UimRoleVO>  getUimRoleByUserId(Long id) {
        Option.of(id).getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_ID_IS_NOT_NULL));
        //根据用户id获取属于该用户的角色信息
        return baseMapper.getUimRoleByUserId(id);
    }

    @Override
    public List<UimResourceVO> getUimResourceByUimSysUserId(Long id) {
        Option.of(id).getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_ID_IS_NOT_NULL));
        //根据用户id获取属于该用户的资源信息
        return baseMapper.getUimResourceByUimSysUserId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UimSysUserVO updateUimSysUserById(Long id, UimSysUserUpdateDTO uimSysUserUpdateDTO, Long userId) {
        log.info("操作者：{}，系统用户id：{}，修改的内容：{}，开始时间：{}",
                userId,id,uimSysUserUpdateDTO,LocalDateTime.now());
        Option.of(id).getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_ID_IS_NOT_NULL));
        UimSysUser uimSysUser = new UimSysUser();
        uimSysUser.setId(id);
        uimSysUser.toUimSysUserUpdateDTO(uimSysUserUpdateDTO);
        uimSysUser.setUpdatedAt(new Date());
        uimSysUser.setUpdatedBy(userId);
       boolean updateUimSysUser =  updateById(uimSysUser);
        if (!updateUimSysUser) {
            log.info("操作者：{}，系统用户id：{}，修改失败的内容：{}，修改失败时间：{}",
                    userId,id,uimSysUserUpdateDTO,LocalDateTime.now());
            throw new BusinessException(UimSysUserEnum.USER_SYS_UPDATE_ERROR);
        }
        log.info("操作者：{}，系统用户id：{}，修改的内容：{}，结束时间：{}",
                userId,id,uimSysUserUpdateDTO,LocalDateTime.now());
        return baseMapper.selectById(id).toUimSysUserVO();
    }

    @Override
    public Page<UimSysUserVO> pageUimSysUserByCondition(String search,
                                                        Long appId,
                                                        Long organId,
                                                        Long branchId,
                                                        int pageNum, int pageSize) {
        Page<UimSysUserVO> pageUimSysUser =
                baseMapper.pageUimSysUserByCondition(search,appId,organId,branchId,new Page<>(pageNum,pageSize));
        return pageUimSysUser;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public UimSysUserVO updateUimSysUserStatusById(Long id, Integer status, Long userId) {
        log.info("系统用户ID：{}，修改内容：{}，操作者：{}，修改开始时间：{}",
                id,status,userId,LocalDateTime.now());

        Option.of(getUimSysUserById(id))
                .getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_IS_NOT_EXISTS));

       boolean updateUimSysUserStatusById = update(Wrappers.<UimSysUser>lambdaUpdate()
                .set(UimSysUser::getStatus,status)
                .set(UimSysUser::getUpdatedBy,userId)
                .set(UimSysUser::getUpdatedAt,new Date())
                .eq(UimSysUser::getId,id));
        if (!updateUimSysUserStatusById) {
            log.info("系统用户ID：{}，修改失败内容：{}，操作者：{}，修改失败时间：{}",
                    id,status,userId,LocalDateTime.now());
            throw  new BusinessException(UimSysUserEnum.USER_SYS_UPDATE_STATUS_ERROR);
        }

        log.info("系统用户ID：{}，修改内容：{}，操作者：{}，修改结束时间：{}",
                id,status,userId,LocalDateTime.now());
        return baseMapper.selectById(id).toUimSysUserVO();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeUimSysUserById(Long id,Long userId) {
        Option.of(getUimSysUserById(id))
                .getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_IS_NOT_EXISTS));
        //删除是假删除即把用户删除状态改为2删除
       boolean removeUimSysUserById =  update(Wrappers.<UimSysUser>lambdaUpdate()
        .set(UimSysUser::getDel,2)
               .set(UimSysUser::getUpdatedBy,userId)
               .set(UimSysUser::getUpdatedAt,new Date())
        .eq(UimSysUser::getId,id));
        return removeUimSysUserById;
    }

    @Override
    public UimUserDetailsVO findUserDetailsByLogin(String loginName) {
        return baseMapper.findUserDetailsByLogin(loginName);
    }

    @Override
    public UimSysUserVO getUimSysUserCode(String code) {

        Option.of(code).getOrElseThrow(()->new BusinessException(UimSysUserEnum.USER_SYS_CODE_IS_NOT_NULL));

        UimSysUser uimSysUser =  baseMapper.selectOne(Wrappers.<UimSysUser>lambdaQuery().select(
                UimSysUser::getId,
                UimSysUser::getIp,
                UimSysUser::getUserName,
                UimSysUser::getCode,
                UimSysUser::getLoginName,
                UimSysUser::getStatus,
                UimSysUser::getAppId,
                UimSysUser::getOrganId,
                UimSysUser::getEmail,
                UimSysUser::getAvatar
        ).eq(UimSysUser::getCode,code));
        if (uimSysUser == null) {
            return null;
        }
        return uimSysUser.toUimSysUserVO();
    }
}
