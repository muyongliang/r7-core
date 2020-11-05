package com.r7.core.job.constant;

import com.r7.core.common.constant.IError;

/**
 * 任务类型错误
 * @author zs
 */
public enum JobErrorEnum implements IError {
    /**
     * 任务不存在
     */
    JOB_IS_NOT_EXISTS("job_is_not_exists", "任务不存在。"),
    JOB_ID_IS_NULL("job_id_is_null", "任务id不能为空"),
    JOB_PROGRESS_IS_NOT_EXISTS("job_progress_is_not_exists", "任务进度不存在。"),
    JOB_PROGRESS_ID_IS_NULL("job_id_is_null", "任务进度id不能为空"),
    JOB_SAVE_ERROR("job_save_error", "任务保存失败"),
    JOB_UPDATE_ERROR("job_update_error", "任务修改失败"),
    JOB_OFF_SHELF_ERROR("job_off_shelf_error", "结束时间大于开始时间"),
    JOB_ID_LENGTH_INCORRECT("job_id_length_is_incorrect", "任务id长度不正确"),
    JOB_USER_ID_LENGTH_INCORRECT("job_user_id_length_incorrect", "任务用户id不存在"),
    JOB_USER_ID_IS_NULL("job_user_id_is_null", "任务用户id不能为空"),
    JOB_ID_IS_NOT_EXISTS("job_id_is_not_exists", "任务id不存在");

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
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
