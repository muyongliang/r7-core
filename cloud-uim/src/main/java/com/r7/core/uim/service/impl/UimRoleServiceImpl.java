package com.r7.core.uim.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.r7.core.common.exception.BusinessException;
import com.r7.core.uim.constant.UimErrorEnum;
import com.r7.core.uim.dto.UimRoleDto;
import com.r7.core.uim.mapper.UimRoleMapper;
import com.r7.core.uim.model.UimRole;
import com.r7.core.uim.service.UimRoleService;
import com.r7.core.uim.vo.UimRoleVo;
import io.vavr.control.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 角色实现层
 *
 * @author zhongpingli
 * @date 2020-09-25
 */
@Slf4j
@Service
public class UimRoleServiceImpl extends ServiceImpl<UimRoleMapper, UimRole> implements UimRoleService {


    @Override
    public UimRoleVo updateRoleById(Long id, UimRoleDto uimRoleDto, Long userId) {
        log.info("角色修改ID:{},修改内容：{}, 修改人ID：{}", id, uimRoleDto, userId);
        id = Option.of(id)
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_ID_IS_NULL));
        UimRole uimRole = Option.of(baseMapper.selectById(id))
                .getOrElseThrow(() -> new BusinessException(UimErrorEnum.ROLE_IS_NOT_EXISTS));
        uimRole.toUimRole(uimRoleDto);
        uimRole.setUpdatedAt(new Date());
        uimRole.setUpdatedBy(userId);
        baseMapper.updateById(uimRole);
        return uimRole.toUimRoleVo();
    }
}
