package com.r7.core.profit.constant;

import com.r7.core.common.constant.IError;

/**
 * @author wutao
 * @Description 发放记录明细错误
 *
 */
public enum CoreRecordIncomeEnum implements IError {

    /**
     * 发放记录明细错误
     */
    CORE_RECORD_INCOME_SAVE_ERROR("core_record_income_save_error","新增发放记录明细失败"),
    CORE_RECORD_INCOME_ID_IS_NULL("core_record_income_id_is_null","发放记录明细id不能为空"),
    CORE_RECORD_INCOME_IS_NOT_EXISTS("core_record_income_is_not_exists","发放记录明细不存在"),
    CORE_RECORD_INCOME_STATUS_UPDATE_ERROR("core_record_income_status_update_error","发放状态修改失败"),
    CORE_ID_LENGTH_ERROR("core_id_length_error","id长度必须是19位")
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    CoreRecordIncomeEnum(String code, String message) {
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
