package com.r7.core.uim.vo;

import com.r7.core.uim.constant.PermissionEnum;
import lombok.Data;

/**
 * 资源信息
 *
 * @author zhongpingli
 */
@Data
public class UimResourceInfoVo {

    private String url;


    private PermissionEnum permission;
}
