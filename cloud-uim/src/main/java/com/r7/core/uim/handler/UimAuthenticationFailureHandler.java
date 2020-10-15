package com.r7.core.uim.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r7.core.common.web.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败相应
 *
 * @author zhongpingli
 */
@Slf4j
@Component("uimAuthenticationFailureHandler")
public class UimAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        log.info("登录失败, 错误异常:{}", exception.getMessage());

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter()
                .write(objectMapper.writeValueAsString(ResponseEntity.failure("login_error", exception.getMessage())));

    }

}
