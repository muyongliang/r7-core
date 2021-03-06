package com.r7.core.uim.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.r7.core.cache.service.RedisService;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.common.fegin.IntegralFeign;
import com.r7.core.common.fegin.ProxyFeign;
import com.r7.core.common.util.CodeUtil;
import com.r7.core.common.util.SnowflakeUtil;
import com.r7.core.common.util.ValidatorUtil;
import com.r7.core.sms.service.SmsService;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.dto.UimUserUpdateDTO;
import com.r7.core.uim.dto.UserSignUpDTO;
import com.r7.core.uim.mapper.UimUserMapper;
import com.r7.core.uim.model.UimUser;
import com.r7.core.uim.service.UimSysUserService;
import com.r7.core.uim.service.UimUserRoleService;
import com.r7.core.uim.service.UimUserService;
import com.r7.core.uim.vo.UimUserDetailsVO;
import com.r7.core.uim.vo.UimUserVO;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户服务实现层
 *
 * @author zhongpingli
 */
@Slf4j
@Service
public class UimUserServiceImpl extends ServiceImpl<UimUserMapper, UimUser> implements UimUserService, UserDetailsService {


    @Resource
    private IntegralFeign integralFeign;

    @Resource
    private ProxyFeign feignProxyService;

    @Resource
    private UimSysUserService uimSysUserService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UimUserRoleService uimUserRoleService;

    @Resource
    private SmsService smsService;

    @Resource
    private RedisService redisService;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        // 普通用户使用电话号 & 系统用户登录账号
        UimUserDetailsVO uimUserDetailsVO = Option.of(baseMapper.findUserDetailsByLogin(loginName))
                .getOrElse(uimSysUserService.findUserDetailsByLogin(loginName));
        Option.of(uimUserDetailsVO).getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_LOGIN_NAME_ERROR));

        // 添加角色
        List<String> listRoleCode = uimUserRoleService.listRoleCode(uimUserDetailsVO.getId());
        Option.of(listRoleCode).exists(x -> {
            uimUserDetailsVO.setRoles(x);
            return false;
        });
        return uimUserDetailsVO;
    }

    @Override
    @Transactional
    public UimUserVO signUpUser(String code, UserSignUpDTO userSignUpDTO, String ip) {
        userSignUpDTO.setPassword(passwordEncoder.encode(userSignUpDTO.getPassword()));
        log.info("新用户注册：{},IP地址{}", userSignUpDTO, ip);

        // 手机格式
        if (!ValidatorUtil.validatorPhoneNumber(userSignUpDTO.getPhoneNumber())) {
            throw new BusinessException(UimErrorEnum.USER_PHONE_ERROR);
        }
        // 验证码校验
        String smsCode = redisService.getKey(userSignUpDTO.getPhoneNumber().toString());
        if (smsCode == null || !smsCode.equals(userSignUpDTO.getCode())) {
            throw new BusinessException(UimErrorEnum.USER_SIGN_UP_SMS_CODE_ERROR);
        }
        // 电话号是否已经存在
        Option.of(getUserByPhone(userSignUpDTO.getPhoneNumber())).exists(eror -> {
            throw new BusinessException(UimErrorEnum.USER_PHONE_EXISTS);
        });

        // code 是否存在
        UimUserVO userByCode = getUserByCode(code);
        Option.of(userByCode)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_CODE_IS_NOT_EXISTS));
        Long id = SnowflakeUtil.getSnowflakeId();
        String userCode = CodeUtil.instance().gen(id);
        // code 是否生成重复
        Option.of(getUserByCode(userCode)).exists(x -> {
            throw new BusinessException(UimErrorEnum.USER_CODE_IS_EXISTS);
        });
        UimUser uimUser = new UimUser();
        uimUser.setId(id);
        uimUser.setOrganId(userByCode.getOrganId());
        uimUser.toUserSingUpDTO(userSignUpDTO);
        uimUser.setCode(userCode);
        uimUser.setAvatar("default");
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

        if (!save) {
            log.info("新用户注册：{}失败,IP地址{}", userSignUpDTO, ip);
            throw new BusinessException(UimErrorEnum.USER_SAVE_ERROR);
        }
        // 用户注册完，新增层级
        feignProxyService.saveCoreProxy(userByCode.getId(), id, userByCode.getOrganId());
        //增加新注册用户的积分信息
        integralFeign.saveCoreIntegralDetail(id, 0);
        //钱包余额初始化

        log.info("新用户注册：{}成功,IP地址{}", userSignUpDTO, ip);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUserAvatar(Long id, String avatar) {
        UimUser uimUser = Option.of(getById(id))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_IS_NOT_EXISTS));
        uimUser.setAvatar(avatar);
        uimUser.setUpdatedBy(id);
        uimUser.setUpdatedAt(new Date());
        return updateById(uimUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean bindUserPhone(Long id, Long phoneNumber, Long code) {
        // 验证码校验
        String smsCode = redisService.getKey(phoneNumber.toString());
        if (smsCode == null || !smsCode.equals(code.toString())) {
            throw new BusinessException(UimErrorEnum.USER_SIGN_UP_SMS_CODE_ERROR);
        }
        UimUser uimUser = Option.of(getById(id))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_IS_NOT_EXISTS));
        uimUser.setPhoneNumber(phoneNumber);
        uimUser.setUpdatedBy(id);
        uimUser.setUpdatedAt(new Date());
        return updateById(uimUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUserPassword(Long id, Long code, String oldPassword, String newPassword) {
        UimUser uimUser = Option.of(getById(id))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.USER_IS_NOT_EXISTS));
        // 验证码校验
        String smsCode = redisService.getKey(uimUser.getPhoneNumber().toString());
        if (smsCode == null || !smsCode.equals(code.toString())) {
            throw new BusinessException(UimErrorEnum.USER_SIGN_UP_SMS_CODE_ERROR);
        }

        if (!passwordEncoder.matches(oldPassword, uimUser.getPassword())) {
            throw new BusinessException(UimErrorEnum.USER_OLD_PASSWORD_ERROR);
        }
        uimUser.setPassword(passwordEncoder.encode(newPassword));
        uimUser.setUpdatedBy(id);
        uimUser.setUpdatedAt(new Date());
        return updateById(uimUser);
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
    public UimUserVO getUserByCode(String code) {
        UimUser uimUser = getOne(Wrappers.<UimUser>lambdaQuery()
                .select(UimUser::getId, UimUser::getAvatar, UimUser::getOrganId,
                        UimUser::getUserName, UimUser::getCode, UimUser::getPhoneNumber)
                .eq(UimUser::getCode, code));
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
    public UimUserVO getUserByPhone(Long phone) {
        UimUser uimUser = getOne(Wrappers.<UimUser>lambdaQuery()
                .select(UimUser::getId, UimUser::getAvatar,
                        UimUser::getUserName, UimUser::getCode, UimUser::getPhoneNumber)
                .eq(UimUser::getPhoneNumber, phone));
        return Option.of(uimUser).map(UimUser::toUimUserVO).getOrNull();
    }

    @Override
    public void sendSmsCode(Long phone, String templateCode) {
        // 手机格式
        if (!ValidatorUtil.validatorPhoneNumber(phone)) {
            throw new BusinessException(UimErrorEnum.USER_PHONE_ERROR);
        }
        String phoneNumber = phone.toString();
        // 是否一分钟内发送的
        Option.of(redisService.getKey(phoneNumber))
                .exists(err -> {
                    throw new BusinessException(UimErrorEnum.USER_SIGN_UP_SMS_SEND_ERROR);
                });
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(1);
        String code = String.valueOf(Math.round((Math.random() + 1) * 100000));
        map.put("code", code);
        smsService.sendSms(phoneNumber, templateCode, JSONUtil.toJsonStr(map));
        redisService.addValue(phoneNumber, code, 60, TimeUnit.SECONDS);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUserPasswordByPhoneNumber(Long phone, Long code, String newPassword) {

        //1、验证手机格式
        if (!ValidatorUtil.validatorPhoneNumber(phone)) {
            throw new BusinessException(UimErrorEnum.USER_PHONE_ERROR);
        }
        //2、验证该手机用户是否存在
        UimUserVO uimUserVO = getUserByPhone(phone);
        if (uimUserVO == null) {
            throw new BusinessException(UimErrorEnum.USER_IS_NOT_EXISTS);
        }

        //3、验证验证码的正确性和有效性
        String smsCode = redisService.getKey(phone.toString());
        if (smsCode == null || !smsCode.equals(code.toString())) {
            throw new BusinessException(UimErrorEnum.USER_SIGN_UP_SMS_CODE_ERROR);
        }
        log.info("手机号:{},验证码:{},新密码:{},操作者:{},开始时间:{}",
                phone, code, newPassword, uimUserVO.getId(), LocalDateTime.now());
        //4、进行密码重新设置
        String password = passwordEncoder.encode(newPassword);
        boolean updateUserPasswordByPhoneNumber = update(Wrappers.<UimUser>lambdaUpdate()
                .set(UimUser::getPassword, password)
                .set(UimUser::getUpdatedAt, new Date())
                .set(UimUser::getUpdatedBy, uimUserVO.getId())
                .eq(UimUser::getId, uimUserVO.getId()));

        if (!updateUserPasswordByPhoneNumber) {
            log.info("手机号:{},验证码:{},新密码:{},操作者:{},普通用户密码重新设置失败时间:{}",
                    phone, code, newPassword, uimUserVO.getId(), LocalDateTime.now());
            return false;
        }
        log.info("手机号:{},验证码:{},新密码:{},操作者:{},重新设置结束时间:{}",
                phone, code, newPassword, uimUserVO.getId(), LocalDateTime.now());
        return true;
    }
}
