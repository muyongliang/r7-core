package com.r7.core.setting.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CoreSettingVO {

    private Long id;

    private Long appId;

    private String key;

    private String settingName;

    private String rule;

    private Integer status;

    private Integer sort;

    private String description;

    private Long createdBy;



    private Long updatedBy;

}
