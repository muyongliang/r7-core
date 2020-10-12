package com.r7.core.uim.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.service.UimUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zs
 * @description: 用户角色关联接口
 * @date : 2020-10-10
 */
@Api(value = "/api/user/role", tags = {"用户角色关联接口"})
@Slf4j
@RestController
@RequestMapping("/user/role")
public class UimUserRoleController {

    @Resource
    private UimUserRoleService uimUserRoleService;

    @ApiOperation(value = "根据用户ID绑定角色", response = Boolean.class)
    @PutMapping("/bind/{bindUserId}")
    public ResponseEntity bindRoleByUserId(@PathVariable("bindUserId") Long bindUserId,
                                           @RequestBody List<Long> roleIds) {
        return ResponseEntity.success(uimUserRoleService
                .bindRoleByUserId(bindUserId, roleIds, 0L, 0L, 0L));
    }


    @ApiOperation(value = "根据用户ID解绑角色", response = Boolean.class)
    @PutMapping("/un/bind/{unBindUserId}")
    public ResponseEntity unBindRoleByUserId(@PathVariable("unBindUserId") Long unBindUserId,
                                           @RequestBody List<Long> roleIds) {
        return ResponseEntity.success(uimUserRoleService
                .unBindRoleByUserId(unBindUserId, roleIds, 0L, 0L, 0L));
    }

    @ApiOperation(value = "根据用户ID解绑全部角色", response = Boolean.class)
    @PutMapping("/remove/{unBindUserId}")
    public ResponseEntity removeRoleByUserId(@PathVariable("unBindUserId") Long unBindUserId) {
        return ResponseEntity.success(uimUserRoleService
                .unBindRoleByUserId(unBindUserId, 0L, 0L, 0L));
    }

    @ApiOperation(value = "根据用户ID查询绑定角色", response = Boolean.class)
    @PutMapping("/list/{userId}")
    public ResponseEntity listRoleByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.success(uimUserRoleService
                .listUimUserRole(userId, 0L, 0L));
    }

    @ApiOperation(value = "根据用户ID或角色ID查询绑定信息", response = Boolean.class)
    @PutMapping("/get/{userId}/{roleId}")
    public ResponseEntity getUimUserRoleByUserIdAndRoleId(@PathVariable("userId") Long userId,
                                                          @PathVariable("roleId") Long roleId) {
        return ResponseEntity.success(uimUserRoleService
                .getUimUserRoleByUserIdAndRoleId(userId, roleId));
    }

}
