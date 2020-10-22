package com.r7.core.uim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.r7.core.uim.model.UimRole;
import com.r7.core.uim.vo.UimRoleVO;
import org.apache.ibatis.annotations.Param;

/**
 * 角色mapper层
 *
 * @author zhongpingli
 * @date 2020-09-25
 */
public interface UimRoleMapper extends BaseMapper<UimRole> {

    /**
     * 分页查询角色
     *
     * @param search  搜索条件
     * @param page    分页
     * @param organId 组织ID
     * @param appId   平台ID
     * @return 返回分页信息
     */
    IPage<UimRoleVO> pageRole(@Param("search") String search,
                              @Param("organId") Long organId,
                              @Param("appId") Long appId,
                              @Param("page") Page<UimRoleVO> page);

}
