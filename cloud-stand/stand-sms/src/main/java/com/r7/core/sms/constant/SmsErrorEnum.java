package com.r7.core.sms.constant;


import com.r7.core.common.constant.IError;

/**
 * 短信异常
 *
 * @author zhongpingli
 */
public enum SmsErrorEnum implements IError {

    /**
     * 短信发送异常
     */
    SMS_SEND_ERROR("sms_send_error", "短信发送失败。");

    private String code;

    private String message;

    SmsErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
