package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.util.CodeUtil;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.common.util.ValidatorUtil;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.constant.UimSysUserDelEnum;
import com.r7.core.uim.constant.UimSysUserEnum;
import com.r7.core.uim.constant.UimSysUserStatusEnum;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 系统用户服务实现层
 *
 * @author zhongpingli
 */
@Slf4j
@Service
public class UimSysUserServiceImpl extends ServiceImpl<UimSysUserMapper, UimSysUser>
        implements UimSysUserService {

    @Resource
    private UimUserService uimUserService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UimSysUserVO saveUimSysUser(String code, UimSysUserDTO uimSysUserDTO, String ip
            , Long appId, Long organId, Long userId) {
        log.info("系统新增用户信息：{}，新增用户IP：{}，平台ID：{}，组织ID：{}，操作者ID：{},新增开始时间：{}",
                uimSysUserDTO, ip, appId, organId, userId, LocalDateTime.now());
        uimSysUserDTO.setPassword(passwordEncoder.encode(uimSysUserDTO.getPassword()));
        //验证部分id的长度
        int length = 19;
        if (String.valueOf(uimSysUserDTO.getBranchId()).length() != length) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_BRANCH_ID_LENGTH_ERROR);
        }
        //验证部门是否存在
        if (getUimSysUserByBranchId(uimSysUserDTO.getBranchId()) == null) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_BRANCH_IS_NOT_EXISTS);
        }
        //验证手机格式
        if (!ValidatorUtil.validatorPhoneNumber(Long.valueOf(uimSysUserDTO.getPhoneNumber()))) {
            throw new BusinessException(UimErrorEnum.USER_PHONE_ERROR);
        }
        //验证邮箱格式
        if (!ValidatorUtil.validatorEmail(uimSysUserDTO.getEmail())) {
            throw new BusinessException(UimSysUserEnum.USER_EMAIL_ERROR);
        }
        //验证手机号是否存在
        if (Optional.ofNullable(getUimSysUserPhoneNumber(uimSysUserDTO.getPhoneNumber()))
                .isPresent()) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_PHONE_IS_EXISTS);
        }
        //验证邮箱是否存在
        if (Optional.ofNullable(getUimSysUserEmail(uimSysUserDTO.getEmail()))
                .isPresent()) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_EMAIL_IS_EXISTS);
        }
        //验证昵称是否存在
        if (Optional.ofNullable(getUimSysUserByUserName(uimSysUserDTO.getUserName()))
                .isPresent()) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_USER_NAME_IS_EXISTS);
        }
        //验证登录用户名是否存在
        if (Optional.ofNullable(findUserDetailsByLogin(uimSysUserDTO.getLoginName()))
                .isPresent()) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_LOGIN_NAME_IS_EXISTS);
        }
        //头像默认default
        if (uimSysUserDTO.getAvatar() == null) {
            uimSysUserDTO.setAvatar("default");
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
        //新增系统用户的状态默认正常，删除默认未删除
        uimSysUser.setStatus(UimSysUserStatusEnum.NORMALSTATUS);
        uimSysUser.setDel(UimSysUserDelEnum.NOTDEL);

        uimSysUser.setOrganId(organId);
        uimSysUser.setCreatedAt(new Date());
        uimSysUser.setUpdatedAt(new Date());
        uimSysUser.setCreatedBy(userId);
        uimSysUser.setUpdatedBy(userId);

        boolean saveUimSysUser = save(uimSysUser);
        if (!saveUimSysUser) {
            log.info("系统新增用户信息失败：{}，新增用户IP：{}，平台ID：{}，组织ID：{}，操作者ID：{},新增失败时间：{}",
                    uimSysUserDTO, ip, appId, organId, userId, LocalDateTime.now());
            throw new BusinessException(UimSysUserEnum.USER_SYS_SAVE_ERROR);
        }

        log.info("系统新增用户信息：{}，新增用户IP：{}，平台ID：{}，组织ID：{}，操作者ID：{},新增结束时间：{}",
                uimSysUserDTO, ip, appId, organId, userId, LocalDateTime.now());
        return uimSysUser.toUimSysUserVO();
    }

    @Override
    public UimSysUserVO getUimSysUserPhoneNumber(String phoneNumber) {

        Option.of(phoneNumber)
                .getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_PHONE_NUMBER_IS_NOT_NULL));

        //验证手机格式是否正确
        if (!ValidatorUtil.validatorPhoneNumber(Long.valueOf(phoneNumber))) {
            throw new BusinessException(UimErrorEnum.USER_PHONE_ERROR);
        }

        UimSysUser uimSysUser = baseMapper.selectOne(Wrappers.<UimSysUser>lambdaQuery()
                .eq(UimSysUser::getPhoneNumber, phoneNumber));
        if (uimSysUser == null) {
            return null;
        }
        return uimSysUser.toUimSysUserVO();
    }

    @Override
    public UimSysUserVO getUimSysUserEmail(String email) {
        Option.of(email)
                .getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_EMAIL_ID_NOT_NULL));

        //验证邮箱格式
        if (!ValidatorUtil.validatorEmail(email)) {
            throw new BusinessException(UimSysUserEnum.USER_EMAIL_ERROR);
        }

        UimSysUser uimSysUser = baseMapper.selectOne(Wrappers.<UimSysUser>lambdaQuery()
                .eq(UimSysUser::getEmail, email));
        if (uimSysUser == null) {
            return null;
        }
        return uimSysUser.toUimSysUserVO();
    }

    @Override
    public UimSysUserVO getUimSysUserByUserName(String userName) {
        Option.of(userName).getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_USER_NAME_IS_NOT_NULL));
        UimSysUser uimSysUser = baseMapper.selectOne(Wrappers.<UimSysUser>lambdaQuery()
                .eq(UimSysUser::getUserName, userName));
        if (uimSysUser == null) {
            return null;
        }
        return uimSysUser.toUimSysUserVO();

    }

    @Override
    public UimSysUserVO getUimSysUserById(Long id) {
        Option.of(id).getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_ID_IS_NOT_NULL));

        int length = 19;
        int idLength = String.valueOf(id).length();
        if (idLength != length) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_ID_LENGTH_ERROR);
        }


        UimSysUser uimSysUser = baseMapper.selectById(id);
        if (uimSysUser == null) {
            return null;
        }
        return uimSysUser.toUimSysUserVO();
    }

    @Override
    public List<UimRoleVO> getUimRoleByUserId(Long id) {
        //验证
        checkSysUserById(id);

        //根据用户id获取属于该用户的角色信息
        return baseMapper.getUimRoleByUserId(id);
    }

    @Override
    public List<UimResourceVO> getUimResourceByUimSysUserId(Long id) {
        //验证
        checkSysUserById(id);

        //根据用户id获取属于该用户的资源信息
        return baseMapper.getUimResourceByUimSysUserId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UimSysUserVO updateUimSysUserById(Long id, UimSysUserUpdateDTO uimSysUserUpdateDTO, Long userId) {
        log.info("操作者：{}，系统用户id：{}，修改的内容：{}，开始时间：{}",
                userId, id, uimSysUserUpdateDTO, LocalDateTime.now());

        //验证
        checkSysUserById(id);

        UimSysUser uimSysUser = new UimSysUser();
        uimSysUser.setId(id);
        uimSysUser.toUimSysUserUpdateDTO(uimSysUserUpdateDTO);
        uimSysUser.setUpdatedAt(new Date());
        uimSysUser.setUpdatedBy(userId);
        boolean updateUimSysUser = updateById(uimSysUser);
        if (!updateUimSysUser) {
            log.info("操作者：{}，系统用户id：{}，修改失败的内容：{}，修改失败时间：{}",
                    userId, id, uimSysUserUpdateDTO, LocalDateTime.now());
            throw new BusinessException(UimSysUserEnum.USER_SYS_UPDATE_ERROR);
        }
        log.info("操作者：{}，系统用户id：{}，修改的内容：{}，结束时间：{}",
                userId, id, uimSysUserUpdateDTO, LocalDateTime.now());
        return baseMapper.selectById(id).toUimSysUserVO();
    }

    @Override
    public Page<UimSysUserVO> pageUimSysUserByCondition(String search,
                                                        Long appId,
                                                        Long organId,
                                                        Long branchId,
                                                        UimSysUserStatusEnum statusTag,
                                                        UimSysUserDelEnum delTag,
                                                        int pageNum, int pageSize) {


        //验证长度
        int length = 19;


        int appIdLength = String.valueOf(appId).length();
        int organIdLength = String.valueOf(organId).length();
        int branchIdLength = String.valueOf(branchId).length();

        if (appId != null && appIdLength != length) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_LENGTH_ERROR);
        }
        if (organId != null && organIdLength != length) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_LENGTH_ERROR);
        }

        if (branchId != null && branchIdLength != length) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_LENGTH_ERROR);
        }

        Integer status;

        if (statusTag == null) {
            status = 0;
        } else {
            status = statusTag.getValue();
        }

        Integer del;
        if (delTag == null) {
            del = 0;
        } else {
            del = delTag.getValue();
        }

        Page<UimSysUserVO> pageUimSysUser =
                baseMapper.pageUimSysUserByCondition(search, appId, organId, branchId, status, del,
                        new Page<>(pageNum, pageSize));
        return pageUimSysUser;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public UimSysUserVO updateUimSysUserStatusById(Long id, UimSysUserStatusEnum status, Long userId) {
        log.info("系统用户ID：{}，修改内容：{}，操作者：{}，修改开始时间：{}",
                id, status, userId, LocalDateTime.now());

        //验证
        checkSysUserById(id);

        boolean updateUimSysUserStatusById = update(Wrappers.<UimSysUser>lambdaUpdate()
                .set(UimSysUser::getStatus, status)
                .set(UimSysUser::getUpdatedBy, userId)
                .set(UimSysUser::getUpdatedAt, new Date())
                .eq(UimSysUser::getId, id));
        if (!updateUimSysUserStatusById) {
            log.info("系统用户ID：{}，修改失败内容：{}，操作者：{}，修改失败时间：{}",
                    id, status, userId, LocalDateTime.now());
            throw new BusinessException(UimSysUserEnum.USER_SYS_UPDATE_STATUS_ERROR);
        }

        log.info("系统用户ID：{}，修改内容：{}，操作者：{}，修改结束时间：{}",
                id, status, userId, LocalDateTime.now());
        return baseMapper.selectById(id).toUimSysUserVO();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeUimSysUserById(Long id, Long userId) {


        log.info("系统用户id:{},操作者:{},开始时间:{}", id, userId, LocalDateTime.now());
        //验证
        checkSysUserById(id);

        //删除是假删除即把用户删除状态改为2删除
        boolean removeUimSysUserById = update(Wrappers.<UimSysUser>lambdaUpdate()
                .set(UimSysUser::getDel, UimSysUserDelEnum.DEL)
                .set(UimSysUser::getUpdatedBy, userId)
                .set(UimSysUser::getUpdatedAt, new Date())
                .eq(UimSysUser::getId, id));
        log.info("系统用户id:{},操作者:{},结束时间:{}", id, userId, LocalDateTime.now());
        return removeUimSysUserById;
    }

    @Override
    public UimUserDetailsVO findUserDetailsByLogin(String loginName) {
        return baseMapper.findUserDetailsByLogin(loginName);
    }

    @Override
    public UimSysUserVO getUimSysUserCode(String code) {

        Option.of(code).getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_CODE_IS_NOT_NULL));

        UimSysUser uimSysUser = baseMapper.selectOne(Wrappers.<UimSysUser>lambdaQuery().select(
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
        ).eq(UimSysUser::getCode, code));
        if (uimSysUser == null) {
            return null;
        }
        return uimSysUser.toUimSysUserVO();
    }

    @Override
    public UimSysUserVO updateUimSysUserPasswordById(Long id, String oldPassword, String password, Long userId) {
        log.info("系统用户ID：{}，旧密码:{},新密码：{}，操作者：{}，开始时间：{}",
                id, oldPassword, password, userId, LocalDateTime.now());

        //验证
        checkSysUserById(id);

        //验证系统用户是否存在
        UimSysUserVO uimSysUserVO = Option.of(getUimSysUserById(id))
                .getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_IS_NOT_EXISTS));
        //判断旧的密码是否正确
        if (!passwordEncoder.matches(oldPassword, uimSysUserVO.getPassword())) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_OLD_PASSWORD_ERROR);
        }
        password = passwordEncoder.encode(password);
        boolean updateUimSysUserPasswordById = update(Wrappers.<UimSysUser>lambdaUpdate()
                .set(UimSysUser::getPassword, password)
                .set(UimSysUser::getUpdatedBy, userId)
                .set(UimSysUser::getUpdatedAt, new Date())
                .eq(UimSysUser::getId, id));

        if (!updateUimSysUserPasswordById) {
            log.info("系统用户ID：{}，旧密码:{},新密码：{}，操作者：{}，密码修改失败时间：{}",
                    id, oldPassword, password, userId, LocalDateTime.now());
            throw new BusinessException(UimSysUserEnum.USER_SYS_PASSWORD_UPDATE_ERROR);
        }
        log.info("系统用户ID：{}，旧密码:{},新密码：{}，操作者：{}，结束时间：{}",
                id, oldPassword, password, userId, LocalDateTime.now());

        return baseMapper.selectById(id).toUimSysUserVO();
    }

    @Override
    public List<UimSysUser> getUimSysUserByBranchId(Long branchId) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.<UimSysUser>lambdaQuery()
                .eq(UimSysUser::getBranchId, branchId);
        List<UimSysUser> list = baseMapper.selectList(lambdaQueryWrapper);

        return list;
    }

    @Override
    public boolean checkSysUserById(Long id) {

        //验证系统用户id是否为空
        Option.of(id).getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_ID_IS_NOT_NULL));

        //验证id的长度
        int length = 19;
        int idLength = String.valueOf(id).length();
        if (idLength != length) {
            throw new BusinessException(UimSysUserEnum.USER_SYS_ID_LENGTH_ERROR);
        }

        //验证系统用户是否存在
        Option.of(getUimSysUserById(id))
                .getOrElseThrow(() -> new BusinessException(UimSysUserEnum.USER_SYS_IS_NOT_EXISTS));

        return true;
    }
}
