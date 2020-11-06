package com.r7.core.uim.controller;

import com.r7.core.common.holder.RequestHolder;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.constant.UimSysUserDelEnum;
import com.r7.core.uim.constant.UimSysUserStatusEnum;
import com.r7.core.uim.dto.UimSysUserDTO;
import com.r7.core.uim.dto.UimSysUserUpdateDTO;
import com.r7.core.uim.service.UimSysUserService;
import com.r7.core.uim.vo.UimResourceVO;
import com.r7.core.uim.vo.UimRoleVO;
import com.r7.core.uim.vo.UimSysUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author wt
 * @Description 系统用户接口
 */
@Slf4j
@Api(value = "/api/sys", tags = {"系统用户接口"})
@RestController
@RequestMapping("/sys/user")
public class UimSysUserController {

    @Resource
    private UimSysUserService uimSysUserService;


    @ApiOperation(value = "系统用户新增", response = UimSysUserVO.class)
    @PostMapping("{code}")
    public ResponseEntity saveUimSysUser(@PathVariable("code") String code,
                                         @Valid @RequestBody UimSysUserDTO uimSysUserDTO,
                                         HttpServletRequest request) {
        return ResponseEntity.success(uimSysUserService.saveUimSysUser(code, uimSysUserDTO,
                request.getRemoteAddr(), RequestHolder.getAppId(), RequestHolder.getOrganId(), RequestHolder.getUserId()));
    }


    @ApiOperation(value = "根据id查询修改系统用户密码", response = UimResourceVO.class)
    @PutMapping("/pwd/{id}")
    public ResponseEntity updateUimSysUserPasswordById(@PathVariable Long id,
                                                       @RequestParam String oldPassword,
                                                       @RequestParam String password) {
        return ResponseEntity.success(uimSysUserService.updateUimSysUserPasswordById(
                id, oldPassword, password, RequestHolder.getUserId()));
    }


    @ApiOperation(value = "根据id查询系统用户信息", response = UimSysUserVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getUimSysUserById(@PathVariable Long id) {
        return ResponseEntity.success(uimSysUserService.getUimSysUserById(id));
    }

    @ApiOperation(value = "根据id查询系统用户信息", response = UimSysUserVO.class)
    @GetMapping("/")
    public ResponseEntity getUimSysUserById() {
        return ResponseEntity.success(uimSysUserService.getUimSysUserById(RequestHolder.getUserId()));
    }

    @ApiOperation(value = "根据用户id删除系统用户信息", response = UimSysUserVO.class)
    @DeleteMapping("/{id}")
    public ResponseEntity removeUimSysUserById(@PathVariable Long id) {
        return ResponseEntity.success(uimSysUserService.removeUimSysUserById(id, RequestHolder.getUserId()));
    }

    @ApiOperation(value = "根据id查询系统用户角色", response = UimRoleVO.class)
    @GetMapping("/role/{id}")
    public ResponseEntity getUimRoleByUserId(@PathVariable Long id) {
        return ResponseEntity.success(uimSysUserService.getUimRoleByUserId(id));
    }


    @ApiOperation(value = "根据id查询系统用户资源", response = UimResourceVO.class)
    @GetMapping("/resource/{id}")
    public ResponseEntity getUimResourceByUimSysUserId(@PathVariable Long id) {
        return ResponseEntity.success(uimSysUserService.getUimResourceByUimSysUserId(id));
    }


    @ApiOperation(value = "根据id查询修改系统用户信息", response = UimResourceVO.class)
    @PutMapping("/{id}")
    public ResponseEntity updateUimSysUserById(@PathVariable Long id,
                                               @Valid @RequestBody UimSysUserUpdateDTO uimSysUserUpdateDTO) {
        return ResponseEntity.success(uimSysUserService.updateUimSysUserById(
                id, uimSysUserUpdateDTO, RequestHolder.getUserId()));
    }


    @ApiOperation(value = "分页查询系统用户信息", response = UimResourceVO.class)
    @GetMapping("/page")
    public ResponseEntity pageUimSysUserByCondition(@RequestParam(value = "search", required = false) String search,
                                                    @RequestParam(value = "appId", required = false) Long appId,
                                                    @RequestParam(value = "organId", required = false) Long organId,
                                                    @RequestParam(value = "branchId", required = false) Long branchId,
                                                    @RequestParam(value = "statusTag", required = false) UimSysUserStatusEnum statusTag,
                                                    @RequestParam(value = "delTag", required = false) UimSysUserDelEnum delTag,
                                                    @RequestParam(defaultValue = "1", value = "pageNum") int pageNum,
                                                    @RequestParam(defaultValue = "2", value = "pageSize") int pageSize) {
        return ResponseEntity.success(uimSysUserService.pageUimSysUserByCondition(search,
                appId, organId, branchId, statusTag, delTag, pageNum, pageSize));
    }

    @ApiOperation(value = "根据id查询修改系统用户状态", response = UimResourceVO.class)
    @PutMapping("/status/{id}")
    public ResponseEntity updateUimSysUserStatusById(@PathVariable Long id,
                                                     @RequestParam UimSysUserStatusEnum status) {
        return ResponseEntity.success(uimSysUserService.updateUimSysUserStatusById(
                id, status, RequestHolder.getUserId()));
    }


//===============测试=================

    @ApiOperation(value = "根据手机查询系统用户信息", response = UimSysUserVO.class)
    @GetMapping("/phone")
    public ResponseEntity getUimSysUserPhoneNumber(@RequestParam String phoneNumber) {
        return ResponseEntity.success(uimSysUserService.getUimSysUserPhoneNumber(phoneNumber));
    }

    @ApiOperation(value = "根据邮箱查询系统用户信息", response = UimSysUserVO.class)
    @GetMapping("/email")
    public ResponseEntity getUimSysUserEmail(@RequestParam String email) {
        return ResponseEntity.success(uimSysUserService.getUimSysUserEmail(email));
    }

    @ApiOperation(value = "根据用户昵称查询系统用户信息", response = UimSysUserVO.class)
    @GetMapping("/userName")
    public ResponseEntity getUimSysUserByUserName(@RequestParam String userName) {
        return ResponseEntity.success(uimSysUserService.getUimSysUserByUserName(userName));
    }


    @ApiOperation(value = "根据部门id查询系统用户信息", response = UimSysUserVO.class)
    @GetMapping("/branch")
    public ResponseEntity getUimSysUserByBranchId(@RequestParam Long branchId) {
        return ResponseEntity.success(uimSysUserService.getUimSysUserByBranchId(branchId));
    }


    @ApiOperation(value = "验证id", response = boolean.class)
    @GetMapping("/check")
    public ResponseEntity checkSysUserById(@RequestParam Long id) {
        return ResponseEntity.success(uimSysUserService.checkSysUserById(id));
    }
}
