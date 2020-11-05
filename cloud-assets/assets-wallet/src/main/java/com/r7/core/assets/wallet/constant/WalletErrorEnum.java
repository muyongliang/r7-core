package com.r7.core.assets.wallet.constant;

import com.r7.core.common.constant.IError;

/**
 * @author zs
 * @description: 钱包异常错误
 * @date : 2020-10-26
 */
public enum WalletErrorEnum implements IError {


    /**
     * 钱包账单返回异常
     */
    WALLET_BILL_SAVE_ERROR("WALLET_BILL_SAVE_ERROR", "钱包账单保存失败"),
    WALLET_BILL_UPDATE_ERROR("wallet_bill_update_error", "钱包账单修改失败"),
    WALLET_BILL_ID_IS_NULL("wallet_bill_id_is_null", "钱包账单id不能为空"),
    WALLET_BILL_IS_NOT_EXISTS("wallet_bill_is_not_exists", "钱包账单不存在"),
    WALLET_BALANCE_SHORTAGE("wallet_balance_shortage", "钱包余额不足"),

    WALLET_EXTRACTION_SAVE_ERROR("wallet_extraction_save_error", "钱包提现明细保存失败"),
    WALLET_EXTRACTION_ID_IS_NULL("wallet_extraction_id_is_null", "钱包提现明细id不能为空"),
    /**
     * 钱包返回异常
     */
    USER_ID_IS_NULL("userId_is_null", "用户id不存在"),
    WALLET_BALANCE_UPDATE_ERROR("wallet_balance_update_error", "钱包余额修改失败"),
    WALLET_SAVE_ERROR("wallet_save_error", "钱包新增失败"),
    WALLET_USER_ID_IS_NOT_EXISTS("wallet_user_id_is_not_exists", "钱包用户不存在"),
    WALLET_USER_ID_IS_NULL("wallet_user_id_is_null", "用户id不存在"),
    WALLET_LOCKING_BALANCE_AMOUNT_IS_ERROR("wallet_locking_balance_amount_is_error", "钱包不可用余额错误"),
    WALLET_BALANCE_AMOUNT_IS_ERROR("wallet_balance_amount_is_error", "钱包余额数量出现错误"),
    WALLET_IS_NOT_EXISTS("wallet_is_not_exists", "钱包对象不存在"),
    WALLET_SIGN_ERROR("wallet_sing_error", "钱包签名错误"),
    WALLET_BALANCE_UPDATE_IS_ERROR("wallet_balance_update_is_error", "钱包余额修改失败"),
    WALLET_BALANCE_IS_NOT_ENOUGH("wallet_balance_is_not_enough", "钱包总余额不足"),
    WALLET_LOCKING_BALANCE_UPDATE_IS_ERROR("wallet_locking_balance_update_is_error", "钱包不可用余额修改失败"),
    WALLET_LOCKING_BALANCE_IS_NOT_ENOUGH("wallet_locking_balance_is_not_enough", "钱包不可用余额不足"),
    WALLET_PAY_PASSWORD_UPDATE_ERROR("wallet_pay_password_update_error", "钱包密码修改错误"),
    WALLET_ID_IS_NOT_EXISTS("wallet_id_is_not_exists", "钱包id不存在"),
    WALLET_ID_IS_NULL("WALLET_ID_IS_NULL", "钱包用户id不能为空"),
    WALLET_PAY_PASSWORD_ERROR("wallet_pay_password_error", "钱包密码错误");

    /**
     * 错误码
     */
    private String code;
    /**
     * 错误信息
     */
    private String message;

    WalletErrorEnum(String code, String message) {
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
