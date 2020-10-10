package com.r7.core.uim.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.service.UimRoleResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色资源关联接口
 *
 * @author zhongpingli
 */
@Api(value = "/api/role/resource", tags = {"角色资源关联接口"})
@Slf4j
@RestController
@RequestMapping("/role/resource")
public class UimRoleResourceController {

    @Resource
    private UimRoleResourceService uimRoleResourceService;


    @ApiOperation(value = "根据角色ID绑定资源", response = Boolean.class)
    @PutMapping("/bind/{roleId}")
    public ResponseEntity bindResourceByRoleId(@PathVariable("roleId") Long roleId,
                                               @RequestBody List<Long> resourceIds) {
        return ResponseEntity.success(uimRoleResourceService
                .bindResourceByRoleId(roleId, resourceIds, 0L, 0L, 0L));
    }

    @ApiOperation(value = "根据角色ID批量解绑角色资源", response = Boolean.class)
    @PutMapping("/unBind/{roleId}")
    public ResponseEntity unBindResourceByRoleId(@PathVariable("roleId") Long roleId,
                                                 @RequestBody List<Long> resourceIds) {
        return ResponseEntity.success(uimRoleResourceService
                .unBindResourceByRoleId(roleId, resourceIds, 0L, 0L, 0L));
    }


    @ApiOperation(value = "根据角色ID解绑所有资源", response = Boolean.class)
    @PutMapping("/unBind/all/{roleId}")
    public ResponseEntity unBindResourceByRoleId(@PathVariable("roleId") Long roleId) {
        return ResponseEntity.success(uimRoleResourceService
                .unBindResourceByRoleId(roleId, 0L, 0L, 0L));
    }

    @ApiOperation(value = "根据角色ID查询绑定的资源", response = Boolean.class)
    @PutMapping("/list/{roleId}")
    public ResponseEntity listUimRoleResource(@PathVariable() Long roleId) {
        return ResponseEntity.success(uimRoleResourceService
                .listUimRoleResource(roleId, 0L, 0L));
    }

}
