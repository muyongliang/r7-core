package com.r7.core.uim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.r7.core.uim.model.UimRoleResource;
import com.r7.core.uim.vo.UimRoleResourceVO;

import java.util.List;

/**
 * 角色资源mapper层
 *
 * @author zhongpingli
 */
public interface UimRoleResourceMapper extends BaseMapper<UimRoleResource> {

    List<UimRoleResourceVO> listUimRoleResourceVo();

}
