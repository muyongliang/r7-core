package com.r7.core.trigger.constant;

import com.r7.core.common.constant.IError;

/**
 * @author wutao
 * @Description 定时任务错误
 * @date 2020/9/28
 */
public enum CoreQuartzJobErrorEnum implements IError {

    CORE_QUARTZ_JOB_IS_NOT_EXISTS("core_quartz_job_is_not_exists","定时任务不存在"),
    CORE_QUARTZ_JOB_ID_IS_NULL("core_quartz_job_id_is_null","定时任务id不能为空"),
    CORE_QUARTZ_JOB_JOB_NAME_IS_NULL ("core_quartz_job_job_name_is_null","定时任务名称不能为空"),
    CORE_QUARTZ_JOB_JOB_GROUP_IS_NULL("core_quartz_job_job_group_is_null","定时任务分组名称不能为空"),
    CORE_QUARTZ_JOB_JOB_CLASS_IS_NULL("core_quartz_job_job_class_is_null","定时任务类路径不能为空"),
    ORE_QUARTZ_JOB_SAVE_ERROR("core_quartz_job_save_error","定时任务添加失败"),
    ORE_QUARTZ_JOB_UPDATE_ERROR("core_quartz_job_update_error","定时任务修改失败"),
    CORE_QUARTZ_JOB_JOB_NAME_IS_NOT_EXISTS("core_quartz_job_job_name_is_not_exists","定时任务名称已存在"),
    CORE_QUARTZ_JOB_JOB_GROUP_IS_NOT_EXISTS("core_quartz_job_job_group_is_not_exists","定时任务名称已存在"),
    CORE_QUARTZ_JOB_JOB_CLASS_IS_NOT_EXISTS("core_quartz_job_job_class_is_not_exists","定时任务名称已存在"),
    CORE_QUARTZ_JOB_JOB_PAUSE_ERROR("core_quartz_job_job_pause_error","暂停失败"),
    CORE_QUARTZ_JOB_JOB_RESUSME_ERROR("core_quartz_job_job_resume_error","恢复失败"),
    CORE_QUARTZ_JOB_JOB_PAUSE_ALL_ERROR("core_quartz_job_job_pause_all_error","全部暂停失败"),
    CORE_QUARTZ_JOB_JOB_RESUME_ALL_ERROR("core_quartz_job_job_resume_all_error","全部恢复失败"),
    CORE_QUARTZ_JOB_JOB_START_ERROR("core_quartz_job_job_start_error","启动失败"),
    CORE_QUARTZ_JOB_JOB_IMMEDIAT_ERROR("core_quartz_job_job_immediat_error","立即执行失败"),
    CORE_QUARTZ_JOB_JOB_OPTIONAL_ERROR("core_quartz_job_job_optional_error","定时任务操作记录添加失败"),
    CORE_QUARTZ_JOB_JOB_REMOVE_ERROR("core_quartz_job_job_remove_error","定时任务删除失败"),
    CORE_QUARTZ_JOB_JOB_STATUS_ERROR("core_quartz_job_job_status_error","定时任务状态修改失败"),
    CORE_QUARTZ_JOB_JOB_COUNT_COUNTFAILURE_ERROR("core_quartz_job_job_count_count_failure_error",
                                                         "定时任务的执行总次数和失败次数修改失败"),
    CORE_QUARTZ_JOB_JOB_UPDATE_RULE_EXPRESSION_ERROR("core_quartz_job_job_update_rule_expression_error",
                                                             "修改定时任务规则失败"),
    CORE_QUARTZ_JOB_JOB_UPDATE_START_AT_ERROR("core_quartz_job_job_update_start_at_error","修改初始化启动时间失败"),
    CORE_QUARTZ_JOB_JOB_UPDATE_REGAIN_AT_ERROR("core_quartz_job_job_update_regain_at_error","修改初始化启动时间失败"),
    CORE_QUARTZ_JOB_JOB_UPDATE_DESCRIPTION_ERROR("core_quartz_job_job_update_description_error",
                                                         "修改定时任务的描述失败");




    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    CoreQuartzJobErrorEnum(String code, String message){
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
