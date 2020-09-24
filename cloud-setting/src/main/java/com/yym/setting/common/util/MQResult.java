package com.yym.setting.common.util;


import cn.hutool.json.JSONUtil;

/**
 * mq result
 */
public class MQResult {

    // 返回码
    private String code;

    // 返回信息提示
    private String msg;

    //类型
    private String mqType;

    // 是否时间限制
    private boolean timeLimit;

    /**
     * 过期时间戳
     */
    private Long timeOutMillis;


    // 返回的数据
    private Object result;

    public MQResult(String mqType) {
        this.mqType = mqType;
    }

    public MQResult(String mqType, String code, String msg, Object result) {
        this.mqType = mqType;
        this.setCode(code);
        this.setMsg(msg);
        this.setResult(result);
        this.timeLimit = false;
        this.timeOutMillis = 0L;
    }

    public MQResult(String mqType, String code, String msg, Object result, boolean timeLimit, Long timeOutMillis) {
        this.mqType = mqType;
        this.code = code;
        this.msg = msg;
        this.result = result;
        this.timeLimit = timeLimit;
        this.timeOutMillis = timeOutMillis;
    }

    @Override
    public String toString() {
        return "Result [code=" + getCode() + ", msg=" + getMsg() + ", result=" + getResult() + "]";
    }

    public String toJSONString() {
        return JSONUtil.toJsonStr(this);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getMqType() {
        return mqType;
    }

    public void setMqType(String mqType) {
        this.mqType = mqType;
    }

    public boolean isTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(boolean timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Long getTimeOutMillis() {
        return timeOutMillis;
    }

    public void setTimeOutMillis(Long timeOutMillis) {
        this.timeOutMillis = timeOutMillis;
    }
}