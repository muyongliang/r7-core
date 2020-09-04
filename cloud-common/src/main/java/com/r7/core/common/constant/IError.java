package com.r7.core.common.constant;

/**
 * 错误类型枚举继承接口
 *
 * @author mrli
 */
public interface IError {

    /**
     * code
     *
     * @return code
     */
    String getCode();

    /**
     * 错误消息
     *
     * @return 消息
     */
    String getMessage();

}
