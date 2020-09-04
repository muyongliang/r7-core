package com.r7.core.common.exception;

import com.r7.core.common.constant.IError;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义业务异常
 *
 * @author mrli
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private String code;

    private String msg;

    private IError error;


    public BusinessException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(IError error) {
        super(error.getMessage());
        this.code = error.getCode();
        this.msg = error.getMessage();
        this.error = error;
    }
}
