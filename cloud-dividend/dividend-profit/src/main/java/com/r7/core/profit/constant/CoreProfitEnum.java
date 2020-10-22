package com.r7.core.profit.constant;

import com.r7.core.common.constant.IError;

/**
 * @author wutao
 * @Description 分润明细错误
 *
 */
public enum CoreProfitEnum implements IError {

    /**
     * 分润错误
     */
    CORE_PROFIT_SAVE_ERROR("core_profit_save_error","分润明细添加失败"),
    CORE_PROFIT_ID_NOT_NULL("core_profit_id_not_null","分润ID不能为空"),
    CORE_PROFIT_IS_NOT_EXISTS("core_profit_is_not_exists","分润明细不存在"),
    CORE_PROFIT_UPDATE_STATUS_ERROR("core_profit_update_status_error","修改分润明细的计算状态失败"),
    CORE_PROFIT_USER_ID_IS_NOT_EXISTS("core_profit_user_id_is_not_exists",
            "截止时间之前用户在该平台的分润明细不存在"),
    CORE_PROFIT_USER_ID_NOT_NULL("core_profit_user_id_not_null","用户id不能为空")
    ;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    CoreProfitEnum(String code, String message) {
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
