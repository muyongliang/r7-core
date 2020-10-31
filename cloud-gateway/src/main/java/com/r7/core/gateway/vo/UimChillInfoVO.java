package com.r7.core.gateway.vo;

import lombok.Data;

import java.util.List;

/**
 * 用户冻结谢谢
 *
 * @author zhongpingli
 */
@Data
public class UimChillInfoVO {

    private Long userId;

    private List<String> resourceUrl;


}
