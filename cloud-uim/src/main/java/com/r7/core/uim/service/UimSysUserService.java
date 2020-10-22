package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.dto.UimSysUserDTO;
import com.r7.core.uim.dto.UimSysUserUpdateDTO;
import com.r7.core.uim.model.UimSysUser;
import com.r7.core.uim.vo.UimResourceVO;
import com.r7.core.uim.vo.UimRoleVO;
import com.r7.core.uim.vo.UimSysUserVO;
import com.r7.core.uim.vo.UimUserDetailsVO;

/**
 * 系统用户服务
 *
 * @author zhongpingli
 */
public interface UimSysUserService extends IService<UimSysUser> {

    /**
     * 新增系统用户
     * @param uimSysUserDTO 新增的系统用户信息
     * @param ip 系统用户注册id
     * @param appId 平台id
     * @param organId 组织id
     * @param userId 操作者id
     * @return 返回新增结果
     */
    UimSysUserVO saveUimSysUser(UimSysUserDTO uimSysUserDTO, String ip
            ,Long appId,Long organId,Long userId);

    /**
     * 验证手机号是否存在
     * @param phoneNumber 手机号码
     * @return 验证结果
     */
    UimSysUserVO getUimSysUserPhoneNumber(String phoneNumber);

    /**
     * 验证邮箱是否存在
     * @param email 邮箱
     * @return 验证结果
     */
    UimSysUserVO getUimSysUserEmail(String email);

    /**
     * 根据系统用户名字获取用户信息
     * @param userName 系统用户名
     * @return 系统用户信息
     */
    UimSysUserVO getUimSysUserByUserName(String userName);

    /**
     * 根据用户ID获取用户信息
     * @param id 系统用户id
     * @return 系统用户信息
     */
    UimSysUserVO getUimSysUserById(Long id);

    /**
     * 根据用户ID获取角色信息
     * @param id 系统用户id
     * @return 相应的角色信息
     */
    UimRoleVO getUimRoleByUserId(Long id);

    /**
     * 根据用户ID获取资源
     * @param id 系统用户id
     * @return 资源信息
     */
    UimResourceVO getUimResourceByUimSysUserId(Long id);

    /**
     * 修改系统用户信息
     * @param id 系统用户id
     * @param uimSysUserUpdateDTO 修改的系统用户信息
     * @param userId 操作者
     * @return 返回修改结果
     */
    UimSysUserVO updateUimSysUserById(Long id, UimSysUserUpdateDTO uimSysUserUpdateDTO,
                                      Long userId);

    /**
     * 根据登录名获取登录信息
     *
     * @param loginName 登录名
     * @return 返回登录信息
     */
    UimUserDetailsVO findUserDetailsByLogin(String loginName);

}
