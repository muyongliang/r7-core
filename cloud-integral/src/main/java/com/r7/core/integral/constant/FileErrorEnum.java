package com.r7.core.integral.constant;

import com.r7.core.common.constant.IError;

/**
 * @Author muyongliang
 * @Date 2020/9/28 10:32
 * @Description 配置模块异常类
 */
public enum FileErrorEnum implements IError {
    /**
     * 异常
     */
    PAGE_SIZE_IS_NULL("page_size_is_null", "分页大小不能为空"),
    PAGE_NUM_IS_NULL("page_num_is_null", "分页页码不能为空"),
    FILE_IS_EMPTY("file_is_empty", "配置信息ID不能为空");

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    FileErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
