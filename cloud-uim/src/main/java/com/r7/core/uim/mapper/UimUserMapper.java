package com.r7.core.uim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.uim.model.UimUser;
import com.r7.core.uim.vo.UimUserDetailsVO;
import com.r7.core.uim.vo.UimUserVO;
import org.apache.ibatis.annotations.Param;

/**
 * 用户mapper
 *
 * @author zhongpingli
 */
public interface UimUserMapper extends BaseMapper<UimUser> {

    /**
     * 根据id获取用户信息
     *
     * @param login 电话号
     * @return 返回信息
     */
    UimUserDetailsVO findUserDetailsByLogin(String login);


    /**
     * 根据条件分页查询用户
     *
     * @param search  查询条件
     * @param organId 组织ID
     * @param page    分页
     * @return 返回信息
     */
    IPage<UimUserVO> pageUser(@Param("search") String search,
                              @Param("organId") Long organId,
                              @Param("page") Page<UimUserVO> page);
}
