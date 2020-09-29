package com.r7.core.uim.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.dto.UimRoleDto;
import com.r7.core.uim.dto.UimRoleSaveDto;
import com.r7.core.uim.service.UimRoleService;
import com.r7.core.uim.vo.UimRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 角色对外接口
 *
 * @author zhongpingli
 * @date 2020-09-25
 */
@Api(value = "/api/role", tags = {"角色接口"})
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
    public ResponseEntity updateRoleById(@PathVariable("id") Long id,
                                         @Valid @RequestBody UimRoleDto uimRoleDto) {
        return ResponseEntity.success(uimRoleService.updateRoleById(id, uimRoleDto, 0L));
    }


    @ApiOperation(
            value = "新增角色",
            response = UimRoleVo.class)
    @PostMapping("")
    public ResponseEntity saveRole(@Valid @RequestBody UimRoleSaveDto uimRoleSaveDto) {
        return ResponseEntity.success(uimRoleService.saveRole(uimRoleSaveDto, 0L, 0L, 0L));
    }

    @ApiOperation(
            value = "根据角色ID删除角色",
            response = Boolean.class)
    @DeleteMapping("/{id}")
    public ResponseEntity removeRoleById(@PathVariable("id") Long id) {
        return ResponseEntity.success(uimRoleService.removeRoleById(id, 0L));
    }

    @ApiOperation(
            value = "根据角色ID批量删除角色",
            response = Boolean.class)
    @DeleteMapping("")
    public ResponseEntity removeRoleByIds(@RequestBody List<Long> ids) {
        return ResponseEntity.success(uimRoleService.removeRoleByIds(ids, 0L));
    }

    @ApiOperation(
            value = "分页查询角色",
            response = UimRoleVo.class)
    @GetMapping("/page")
    public ResponseEntity pageRole(@RequestParam(value = "search", required = false) String search,
                                   @RequestParam(value = "pageNum", defaultValue = "1") long pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "15") long pageSize) {
        return ResponseEntity.success(uimRoleService.pageRole(search, pageNum, pageSize));
    }

    @ApiOperation(
            value = "根据ID查询角色详情",
            response = UimRoleVo.class)
    @GetMapping("/{id}")
    public ResponseEntity getRoleById(@PathVariable("id") Long id) {
        return ResponseEntity.success(uimRoleService.getRoleById(id));
    }

}
