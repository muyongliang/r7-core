package com.r7.core.uim.controller;

import com.r7.core.common.holder.RequestHolder;
import com.r7.core.common.web.ResponseEntity;
import com.r7.core.uim.dto.UimSysUserDTO;
import com.r7.core.uim.dto.UserSignUpDTO;
import com.r7.core.uim.service.UimSysUserService;
import com.r7.core.uim.vo.UimSysUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wt
 * @Description
 */
@Slf4j
@Api(value = "/api/sys", tags = {"系统用户接口"})
@RestController
@RequestMapping("/sys/user")
public class UimSysUserController {


    @Autowired
    private UimSysUserService uimSysUserService;


    @ApiOperation(value = "系统用户新增", response = UimSysUserVO.class)
    @PostMapping("")
    public ResponseEntity signUpUser(@RequestBody UimSysUserDTO uimSysUserDTO,
                                     HttpServletRequest request) {
        return ResponseEntity.success(uimSysUserService.saveUimSysUser(uimSysUserDTO,
                request.getRemoteAddr(), RequestHolder.getAppId(), RequestHolder.getOrganId(),RequestHolder.getUserId()));
    }
}
