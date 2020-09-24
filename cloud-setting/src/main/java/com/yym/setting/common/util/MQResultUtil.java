package com.yym.setting.common.util;


import cn.hutool.json.JSONUtil;
import com.yym.setting.common.enums.MQEnum;
import com.yym.setting.common.enums.RetEnum;

/**
 * @Description: mq  返回 封装
 * @author: jinghan
 * @date 2019/4/24 15:29
 */
public class MQResultUtil {
    /**
     * 数据交互成功返回
     *
     * @param object json返回的数据
     */
    public static MQResult success(MQEnum mqEnum, Object object) {

        if (object == null) {
            object = "";
        }
        return new MQResult(mqEnum.toString(), RetEnum.RET_SUCCESS.getCode(), RetEnum.RET_SUCCESS.getMessage(), object);
    }

    /**
     * 带有时间限制的 数据交互成功返回
     *
     * @param object json返回的数据
     */
    public static MQResult success(MQEnum mqEnum, Object object, Long timeOutMillis) {

        if (object == null) {
            object = "";
        }
        return new MQResult(mqEnum.toString(),
                RetEnum.RET_SUCCESS.getCode(),
                RetEnum.RET_SUCCESS.getMessage(),
                object,
                true,
                timeOutMillis
        );
    }


    /**
     * 异常处理
     *
     * @param retEnum
     * @return
     */
    public static MQResult error(MQEnum mqEnum, RetEnum retEnum) {
        return new MQResult(mqEnum.toString(), retEnum.getCode(), retEnum.getMessage(), "");
    }

    /**
     * 异常处理
     *
     * @param retEnum
     * @return
     */
    public static MQResult error(MQEnum mqEnum, RetEnum retEnum, Object result) {
        return new MQResult(mqEnum.toString(), retEnum.getCode(), retEnum.getMessage(), result);
    }

    public static MQResult error(MQEnum mqEnum, String code, String msg) {
        return new MQResult(mqEnum.toString(), code, msg, "");
    }

    public static MQResult error(MQEnum mqEnum, String code, String msg, Object result) {
        return new MQResult(mqEnum.toString(), code, msg, result);
    }

    /**
     * 系统异常
     */
    public static MQResult systemError(MQEnum mqEnum, String message) {
        return new MQResult(mqEnum.toString(), RetEnum.RET_UNKNOWN_ERROR.getCode(), message, "");
    }

    /**
     * 系统异常
     */
    public static MQResult systemError(MQEnum mqEnum, String message, Object result) {
        return new MQResult(mqEnum.toString(), RetEnum.RET_UNKNOWN_ERROR.getCode(), message, result);
    }

    /**
     * 系统异常
     */
    public static MQResult systemError(MQEnum mqEnum, Object result) {
        return new MQResult(mqEnum.toString(), RetEnum.RET_UNKNOWN_ERROR.getCode(), RetEnum.RET_UNKNOWN_ERROR.getMessage(), result);
    }

    /**
     * String  字符串 转  Resulit
     *
     * @param str
     * @return
     */
    public static MQResult strToResult(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return JSONUtil.toBean(str, MQResult.class);
    }

    /**
     * 判断成功
     *
     * @param result
     * @return
     */
    public static boolean isSuccess(MQResult result) {
        if (result == null) {
            return false;
        }
        if (RetEnum.RET_SUCCESS.getCode().equals(result.getCode())) {
            return true;
        }
        return false;
    }

    /**
     * 判断未成功
     *
     * @param result
     * @return
     */
    public static boolean isNotSuccess(MQResult result) {
        return !isSuccess(result);
    }
}