package com.r7.core.uim.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.dto.UimRoleDto;
import com.r7.core.uim.service.UimRoleService;
import com.r7.core.uim.vo.UimRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色对外接口
 *
 * @author zhongpingli
 * @date 2020-09-25
 */
@Api(value = "/role", tags = {"角色接口"})
@RestController
@RequestMapping("/role")
public class UimRoleController {

    @Resource
    private UimRoleService uimRoleService;


    @ApiOperation(
            value = "根据角色ID修改角色信息",
            notes = "角色ID一定要存在",
            response = UimRoleVo.class)
    @PutMapping("/{id}")
    public ResponseEntity updateRoleById(@PathVariable("id") Long id, @RequestBody UimRoleDto uimRoleDto) {
        return ResponseEntity.success(uimRoleService.updateRoleById(id, uimRoleDto, 0L));
    }

}