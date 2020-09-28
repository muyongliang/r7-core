package com.r7.core.job.constant;

import com.r7.core.common.constant.IError;

/**
 * @author zs
 */
public enum JobErrorEnum implements IError {
    /**
     * 任务不存在
     */
    JOB_IS_NOT_EXISTS("job_is_not_exists", "任务不存在。"),
    JOB_ID_IS_NULL("job_id_is_null", "任务id不能为空"),
    JOB_PROGRESS_IS_NOT_EXISTS("job_progress_is_not_exists", "任务进度不存在。"),
    JOB_PROGRESS_ID_IS_NULL("job_id_is_null", "任务进度id不能为空");

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    JobErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
