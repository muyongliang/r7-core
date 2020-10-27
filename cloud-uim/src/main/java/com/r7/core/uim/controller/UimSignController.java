package com.r7.core.uim.controller;

import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.dto.UserSignUpDTO;
import com.r7.core.uim.service.UimUserService;
import com.r7.core.uim.vo.UimUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 注册
 *
 * @author zhongpingli
 */
@Slf4j
@Api(value = "/api/sign", tags = {"注册"})
@RestController
@RequestMapping("/sign")
public class UimSignController {


    @Resource
    private UimUserService uimUserService;

    @ApiOperation(value = "注册", response = UimUserVO.class)
    @PostMapping("/up/{code}")
    public ResponseEntity signUpUser(@PathVariable("code") String code,
                                     @RequestBody UserSignUpDTO userSignUpDTO, HttpServletRequest request) {
        return ResponseEntity.success(uimUserService.signUpUser(code, userSignUpDTO, request.getRemoteAddr()));
    }

    @ApiOperation(value = "注册手机验证码", response = UimUserVO.class)
    @PostMapping("/sms/code/{phone}")
    public ResponseEntity sendSmsCode(@PathVariable("phone") String phone) {
        uimUserService.sendSmsCode(phone);
        return ResponseEntity.success();
    }

}
