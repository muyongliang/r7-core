package com.r7.core.uim.controller;

import com.r7.core.common.web.ResponseEntity;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(value = "api/oauth", tags = {"认证接口"})
@Slf4j
@RestController
@RequestMapping("/oauth")
public class UimOauthController {


    /**
     * 当需要身份认证时，跳转到这里
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity requireAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.failure("user_is_not_login", "用户未登录");
    }

}
