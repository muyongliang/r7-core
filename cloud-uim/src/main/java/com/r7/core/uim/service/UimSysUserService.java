package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.dto.UimSysUserDTO;
import com.r7.core.uim.dto.UimSysUserUpdateDTO;
import com.r7.core.uim.model.UimSysUser;
import com.r7.core.uim.vo.UimResourceVO;
import com.r7.core.uim.vo.UimRoleVO;
import com.r7.core.uim.vo.UimSysUserVO;
import com.r7.core.uim.vo.UimUserDetailsVO;

import java.util.List;

/**
 * 系统用户服务
 *
 * @author zhongpingli
 */
public interface UimSysUserService extends IService<UimSysUser> {

    /**
     * 新增系统用户
     *
     * @param code          邀请码
     * @param uimSysUserDTO 新增的系统用户信息
     * @param ip            系统用户注册id
     * @param appId         平台id
     * @param organId       组织id
     * @param userId        操作者id
     * @return 返回新增结果
     */
    UimSysUserVO saveUimSysUser(String code, UimSysUserDTO uimSysUserDTO, String ip
            , Long appId, Long organId, Long userId);

    /**
     * 根据手机查询手系统用户信息
     * @param phoneNumber 手机号码
     * @return 验证结果
     */
    UimSysUserVO getUimSysUserPhoneNumber(String phoneNumber);

    /**
     * 根据邮箱查询系统用户信息
     * @param email 邮箱
     * @return 验证结果
     */
    UimSysUserVO getUimSysUserEmail(String email);

    /**
     * 根据系统用户昵称字获取用户信息
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
     *
     * @param id 系统用户id
     * @return 相应的角色信息
     */
    List<UimRoleVO> getUimRoleByUserId(Long id);

    /**
     * 根据用户ID获取资源
     *
     * @param id 系统用户id
     * @return 资源信息
     */
    List<UimResourceVO> getUimResourceByUimSysUserId(Long id);

    /**
     * 修改系统用户信息
     *
     * @param id                  系统用户id
     * @param uimSysUserUpdateDTO 修改的系统用户信息
     * @param userId              操作者
     * @return 返回修改结果
     */
    UimSysUserVO updateUimSysUserById(Long id, UimSysUserUpdateDTO uimSysUserUpdateDTO,
                                      Long userId);


    /**
     * 分页查询系统用户
     *
     * @param search   搜索条件
     * @param appId    平台id
     * @param organId  组织id
     * @param branchId 组织id
     * @param pageNum  当前页
     * @param pageSize 每页显示数
     * @return 分页查询结果
     */
    Page<UimSysUserVO> pageUimSysUserByCondition(String search,
                                                 Long appId,
                                                 Long organId,
                                                 Long branchId,
                                                 int pageNum, int pageSize);

    /**
     * 根据系统用户id修改状态
     *
     * @param id     系统用户id
     * @param status 系统用户状态
     * @param userId 操作者id
     * @return 返回修改结果
     */
    UimSysUserVO updateUimSysUserStatusById(Long id, Integer status, Long userId);

    /**
     * 根据系统用户id进行删除
     *
     * @param id     系统用户id
     * @param userId 操作者id
     * @return 删除结果
     */
    boolean removeUimSysUserById(Long id, Long userId);


    /**
     * 根据邀请码查询系统用户信息
     *
     * @param code 邀请码
     * @return 系统用户信息
     */
    UimSysUserVO getUimSysUserCode(String code);

    /**
     * 根据登录名获取登录信息
     *
     * @param loginName 登录名
     * @return 返回登录信息
     */
    UimUserDetailsVO findUserDetailsByLogin(String loginName);



}
