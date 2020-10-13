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

    @ApiOperation(value = "根据用户ID查询绑定角色", response = Boolean.class)
    @PutMapping("/list/{userId}")
    public ResponseEntity listRoleByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.success(uimUserRoleService
                .listUimUserRole(userId, 0L, 0L));
    }

}
