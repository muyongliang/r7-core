package com.r7.core.setting.common.enums;

/**
Enum集合类
 */
public class PhoneDataEnum {

    /**
     * 号码 渠道运营商 (0：中国移动，1、中国联通，2、中国电信 )
     */
    public enum PhoneOperator {
        /**
         * 号码 渠道运营商 (0：中国移动，1、中国联通，2、中国电信 )
         */
        China_Mobile(0),
        China_Unicom(1),
        China_Telecom(2),
        ;
        private Integer code;

        private PhoneOperator(Integer value) {
            this.code = value;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 手机号touch下单状态 （0 正常  1禁用）
     */
    public enum PhoneTouchStatus {
        NORMAL(0),

        FORBIDDEN(1),
        ;
        private Integer code;

        private PhoneTouchStatus(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

}
