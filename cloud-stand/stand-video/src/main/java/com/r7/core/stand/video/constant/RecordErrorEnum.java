package com.r7.core.stand.video.constant;

import com.r7.core.common.constant.IError;
import org.apache.commons.lang3.StringUtils;

/**
 * @author muyongliang
 * @date 2020/10/29 10:20
 * @description 视频录制异常类
 */
public enum RecordErrorEnum implements IError {
    /**
     * 异常
     */
    VIDEO_NOT_EXIST("录制视频不存在,录制失败"),
    MIX__RESOLUTION_ERROR("mixResolution配置错误"),
    FAILURE_UPLOAD_FILE("上传文件失败");
    /**
     * 错误信息
     */
    private String message;

    RecordErrorEnum(String message) {
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public RecordErrorEnum setMessage(String message) {
        if (StringUtils.isNotBlank(message)) {
            this.message = message;
        }
        return this;
    }
}
