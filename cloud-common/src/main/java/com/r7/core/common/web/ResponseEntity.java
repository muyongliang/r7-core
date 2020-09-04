package com.r7.core.common.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.r7.core.common.constant.IError;

/**
 * 响应JSON数据
 *
 * @author zhongpingli
 */
@JsonView(Views.Public.class)
public class ResponseEntity {
    private boolean isSuccess;
    private Object data;
    private String errorCode;
    private String errorMsg;

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static ResponseEntity success(Object data) {
        ResponseEntity response = new ResponseEntity();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    /**
     * 成功
     *
     * @return
     */
    public static ResponseEntity success() {
        ResponseEntity response = new ResponseEntity();
        response.setSuccess(true);
        return response;
    }

    /**
     * 失败
     *
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public static ResponseEntity failure(String errorCode, String errorMsg) {
        ResponseEntity response = new ResponseEntity();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setErrorMsg(errorMsg);
        return response;
    }


    /**
     * 失败
     *
     * @return
     */
    public static ResponseEntity failure() {
        return failure();
    }

    /**
     * 失败
     *
     * @param error
     * @return
     */
    public static ResponseEntity failure(IError error, Object data) {
        ResponseEntity response = new ResponseEntity();
        response.setSuccess(false);
        String code = error.getCode();
        if(code == null) {
            code = error.toString().toLowerCase();
        }


        response.setErrorCode(code);
        response.setErrorMsg(error.getMessage());
        response.setData(data);
        return response;
    }

    /**
     * 失败
     *
     * @param error
     * @return
     */
    public static ResponseEntity failure(IError error) {
        ResponseEntity response = new ResponseEntity();
        response.setSuccess(false);
        String code = error.getCode();
        if(code == null) {
            code = error.toString().toLowerCase();
        }
        response.setErrorCode(code);
        response.setErrorMsg(error.getMessage());
        return response;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
