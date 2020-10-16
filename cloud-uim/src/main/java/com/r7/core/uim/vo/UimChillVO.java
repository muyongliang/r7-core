package com.r7.core.uim.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zs
 * @description: 冻结信息展示
 * @date : 2020-10-13
 */
@Data
@ApiModel("冻结信息展示")
public class UimChillVO {
    @ApiModelProperty("id")
    private Long id;
    /**
     * 冻结用户
     */
    @ApiModelProperty("冻结用户")
    private Long userId;
    /**
     * 冻结资源;登录/邀请码/提现/平台冻结
     */
    @ApiModelProperty("冻结资源id")
    private Long resourceId;
    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
}
