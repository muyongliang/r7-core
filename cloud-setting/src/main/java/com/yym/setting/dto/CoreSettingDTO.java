package com.yym.setting.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CoreSettingDTO {

    private Long id;

    private Long appId;

    private String key;

    private String settingName;

    private String rule;

    private Integer status;

    private Integer sort;

    private String description;

    private Long createdBy;

    private Date createdAt;

    private Long updatedBy;

    private Date updatedAt;
}
