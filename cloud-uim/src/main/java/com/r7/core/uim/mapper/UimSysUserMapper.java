package com.r7.core.uim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.uim.model.UimSysUser;
import com.r7.core.uim.vo.UimResourceVO;
import com.r7.core.uim.vo.UimRoleVO;
import com.r7.core.uim.vo.UimSysUserVO;
import com.r7.core.uim.vo.UimUserDetailsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户mapper层
 *
 * @author zhongpingli
 */
public interface UimSysUserMapper extends BaseMapper<UimSysUser> {


    /**
     * 分页查询系统用户
     *
     * @param search   查询条件
     * @param appId    平台id
     * @param organId  组织id
     * @param branchId 部门id
     * @param page     分页
     * @return 查询结果
     */
    Page<UimSysUserVO> pageUimSysUserByCondition(@Param("search") String search,
                                                 @Param("appId") Long appId,
                                                 @Param("organId") Long organId,
                                                 @Param("branchId") Long branchId,
                                                 @Param("page") Page<UimSysUserVO> page);


    /**
     * 根据用户ID获取资源
     *
     * @param id 系统用户id
     * @return 资源信息
     */
    List<UimResourceVO> getUimResourceByUimSysUserId(Long id);

    /**
     * 根据用户ID获取角色信息
     *
     * @param id 系统用户id
     * @return 相应的角色信息
     */
    List<UimRoleVO> getUimRoleByUserId(Long id);

    /**
     * 根据id获取用户信息
     *
     * @param login 登陆账户
     * @return
     */
    UimUserDetailsVO findUserDetailsByLogin(String login);

}
