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

}
