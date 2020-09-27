package com.r7.core.integral.constant;

/**
 * 用于包装结果的返回结果类
 * @author zs
 */
public class JsonResult<T> {
    private Integer status;
    private String message;
    private T data;

    public JsonResult() {
    }

    public JsonResult(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public JsonResult(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
