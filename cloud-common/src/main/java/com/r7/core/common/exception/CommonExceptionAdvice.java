package com.r7.core.common.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.r7.core.common.constant.CommitErrorEnum;
import com.r7.core.common.web.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 控制层异常
 *
 * @author mrli
 */
@Slf4j
@ControllerAdvice
public class CommonExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    ResponseEntity handelBusinessException(HttpServletRequest request, BusinessException e) {
        return ResponseEntity.failure(e.getCode(), e.getMsg());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(HttpServletRequest request,
                                                                MethodArgumentNotValidException ex) {
        if (ex.getBindingResult().hasErrors()) {
            List<FieldError> errors = ex.getBindingResult().getFieldErrors();

            return ResponseEntity.failure(errors.get(0).getField() + "_error", errors.get(0).getDefaultMessage());

        }
        return ResponseEntity.failure(CommitErrorEnum.ARGUMENT_NOT_VALID.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBindExceptionException(BindException ex) {
        if (ex.getBindingResult().hasErrors()) {
            List<FieldError> errors = ex.getBindingResult().getFieldErrors();
            return ResponseEntity.failure(errors.get(0).getField() + "_error", errors.get(0).getDefaultMessage());
        }
        return ResponseEntity.failure(CommitErrorEnum.ARGUMENT_NOT_VALID.getCode(), ex.getMessage());
    }
    /**
     * 方法调用错误使用
     *
     * @param request
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleMethodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response,
                                                                HttpRequestMethodNotSupportedException ex) {
        // 设置为找不到页面
        response.setStatus(404);
        log.info("Uri: " + request.getRequestURI() + ", Method: " + request.getMethod() + ", " + ex.getMessage());
        return ResponseEntity.failure(CommitErrorEnum.UNVALID_API);
    }

    /**
     * 如果 Jackson 解析参数错误，抛出 400 的错误.
     *
     * @param request 客户端请求.
     * @param ex      Jackson 异常
     * @return 400 的错误
     */
    @ResponseBody
    @ExceptionHandler({JsonProcessingException.class, HttpMessageNotReadableException.class})
    public ResponseEntity handleClientParamException(HttpServletRequest request, Exception ex) {
        log.debug("Request uri[{}] param error, ", request.getRequestURI(), ex);
        return ResponseEntity.failure("param error", "参数不能解析或类型不对");
    }

    /**
     * 总的异常：
     * <p>
     * Exception只有在找不到异常类型绑定时才会到达这里处理,
     * 这些异常无须让调用者知道，属于系统内部错误
     * </p>
     *
     * @param request
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleException(HttpServletRequest request, Exception ex) {
        log.error(request.getRequestURI(), ex);
        return ResponseEntity.failure("common.error.internal", "内部异常");
    }

}
