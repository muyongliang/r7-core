package com.r7.core.uim.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.dto.UimRoleDto;
import com.r7.core.uim.model.UimRole;
import com.r7.core.uim.vo.UimRoleVo;

/**
 * 角色服务
 *
 * @author zhongpingli
 * @date 2020-09-25
 */
public interface UimRoleService extends IService<UimRole> {


    /**
     * 根据ID修改角色
     *
     * @param id         角色ID
     * @param uimRoleDto 角色修改谢谢
     * @param userId     操作用户ID
     * @return 返回修改结果
     */
    UimRoleVo updateRoleById(Long id,
                             UimRoleDto uimRoleDto,
                             Long userId);


}
