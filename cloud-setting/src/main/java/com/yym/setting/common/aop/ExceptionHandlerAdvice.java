package com.yym.setting.common.aop;

import com.yym.setting.common.annotation.CommonRestResponse;
import com.yym.setting.common.domain.Result;
import com.yym.setting.common.enums.RetEnum;
import com.yym.setting.common.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理器
 *
 */
@ControllerAdvice(annotations = CommonRestResponse.class)
@ResponseBody
@Slf4j
@Order(0)
public class ExceptionHandlerAdvice {
    /**
     * 处理未捕获的Exception
     *
     * @param e 异常
     * @return 统一响应体
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new Result(RetEnum.RET_UNKNOWN_ERROR.getCode(), RetEnum.RET_UNKNOWN_ERROR.getMessage(), null);
    }


    /**
     * 处理未捕获的RuntimeException
     *
     * @param e 运行时异常
     * @return 统一响应体
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return new Result(RetEnum.RET_UNKNOWN_ERROR.getCode(), RetEnum.RET_UNKNOWN_ERROR.getMessage(), null);
    }

    /**
     * 处理业务异常BaseException
     *
     * @param e 业务异常
     * @return 统一响应体
     */
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        RetEnum code = e.getCode();
        return new Result(code.getCode(), code.getMessage(), e.getMessage());
    }
}

