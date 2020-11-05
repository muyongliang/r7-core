package com.r7.core.assets.wallet.constant;

import com.r7.core.common.constant.IError;

/**
 * @author zs
 * @description: 核心钱包提现明细异常错误
 * @date : 2020-10-28
 */
public enum WalletExtractionErrorEnum implements IError {

    /**
     * 钱包提现明细
     */
    WALLET_EXTRACTION_SAVE_ERROR("wallet_extraction_save_error", "钱包提现明细保存错误"),
    WALLET_EXTRACTION_ID_IS_EXISTS("wallet_extraction_id_is_exists", "钱包提现明细id不存在"),
    WALLET_EXTRACTION_UPDATE_ERROR("wallet_extraction_update_error", "钱包"),
    WALLET_EXTRACTION_ID_IS_NOT_EXISTS("wallet_extraction_id_is_not_exists", "钱包提现明细id不存在"),
    WALLET_EXTRACTION_ID_IS_NULL("wallet_extraction_id_is_null", "钱包提现明细id不能为空"),
    WALLET_EXCEPTION_USER_ID_LENGTH_INCORRECT("wallet_exception_user_id_length_is_incorrect", "钱包提现明细用户id长度不正确"),
    WALLET_EXCEPTION_ID_LENGTH_IS_INCORRECT("wallet_exception_id_length_is_incorrect", "钱包提现明细id长度不正确"),
    WALLET_EXCEPTION_OUT_ORDER_SN_LENGTH_IS_INCORRECT("wallet_exception_out_order_sn_length_is_incorrect", "钱包提现明细外部订单号长度不正确");

    /**
     * 错误码
     */
    private String code;
    /**
     * 异常消息
     */
    private String message;

    WalletExtractionErrorEnum(String code, String message) {
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
