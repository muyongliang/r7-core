package com.r7.core.uim.exception;

import com.r7.core.common.web.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class UimExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidGrantException.class)
    ResponseEntity handleBusinessException(HttpServletRequest request, InvalidGrantException e) {
        return ResponseEntity.failure(e.getOAuth2ErrorCode(), e.getMessage());
    }
}
