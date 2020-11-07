package com.r7.core.uim.controller;


import com.r7.core.common.holder.RequestHolder;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.service.UimUserService;
import com.r7.core.uim.vo.UimUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户控制层
 *
 * @author zhongpingli
 */
@Slf4j
@RestController
@Api(value = "/api/user", tags = {"用户接口"})
@RequestMapping("/user")
public class UimUserController {


    @Resource
    private UimUserService uimUserService;

    @ApiOperation(value = "获取当前登录用户信息", response = UimUserVO.class)
    @GetMapping("")
    public ResponseEntity getUserById() {
        return ResponseEntity.success(uimUserService.getUserById(RequestHolder.getUserId()));
    }

    @ApiOperation(value = "获取当前登录用户信息", response = UimUserVO.class)
    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.success(uimUserService.getUserById(id));
    }

    @ApiOperation(value = "修改头像", response = Boolean.class)
    @PutMapping("/avatar/{avatar}")
    public ResponseEntity updateUserAvatar(@PathVariable("avatar") String avatar) {
        return ResponseEntity.success(uimUserService.updateUserAvatar(RequestHolder.getUserId(), avatar));
    }


    @ApiOperation(value = "换绑手机", response = Boolean.class)
    @PutMapping("/bind/{phoneNumber}")
    public ResponseEntity bindUserPhone(@PathVariable("phoneNumber") Long phoneNumber,
                                        @RequestParam Long code) {
        return ResponseEntity.success(uimUserService.bindUserPhone(RequestHolder.getUserId(), phoneNumber, code));
    }


    @ApiOperation(value = "修改密码", response = Boolean.class)
    @PutMapping("/password/{code}")
    public ResponseEntity updateUserPassword(@PathVariable("code") Long code,
                                             @RequestParam String oldPassword,
                                             @RequestParam String newPassword) {
        return ResponseEntity.success(uimUserService
                .updateUserPassword(RequestHolder.getUserId(), code, oldPassword, newPassword));
    }

    @ApiOperation(value = "手机换绑验证码", response = UimUserVO.class)
    @PostMapping("/sms/code/{phone}")
    public ResponseEntity sendBindPhoneSmsCode(@PathVariable("phone") Long phone) {
        uimUserService.sendSmsCode(phone, "SMS_165215124");
        return ResponseEntity.success();
    }
}
