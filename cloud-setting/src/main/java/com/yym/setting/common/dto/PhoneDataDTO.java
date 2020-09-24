package com.yym.setting.common.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PhoneDataDTO {

    private Long phoneId;
    @NotNull
    private Long phoneNumber;

    private Byte phoneOperator;

    private String phoneProvName;

    private String phoneProvCode;

    private String phoneAreaName;

    private String phoneAreaCode;

    private Byte phoneTouchStatus;

    private String phoneTouchErrorCode;

    private String phoneTouchErrorMsg;
}
