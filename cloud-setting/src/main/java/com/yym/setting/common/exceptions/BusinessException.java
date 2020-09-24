package com.yym.setting.common.exceptions;


import com.yym.setting.common.enums.RetEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常类，继承运行时异常，确保事务正常回滚
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BusinessException extends RuntimeException {

    private RetEnum code;

    public BusinessException(RetEnum code) {
        this.code = code;
    }

    public BusinessException(Throwable cause, RetEnum code) {
        super(cause);
        this.code = code;
    }
}

