package com.r7.core.integral.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wt
 * @Description 当前用户积分视图
 */
@Data
@ApiModel(value = "当前用户积分视图")
public class CoreIntegralVO {


    /**
     * id
     */
    @TableId(value = "id")
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 当前用户id
     */
    @ApiModelProperty(value = "当前用户id")
    private Long userId;

    /**
     * 当前用户的总积分
     */
    @ApiModelProperty(value = "当前用户的总积分")
    private Integer total;

    /**
     * 签名 用户ID，用户总积分，每次增减时，从数据库中获取签名进行对比，如果匹配，则进行增减操作，并重新设置对应的签名，否者返回对应错误
     */
    @ApiModelProperty(value = "签名 用户ID，用户总积分，每次增减时，从数据库中获取签名进行对比，如果匹配，则进行增减操作，并重新设置对应的签名，否者返回对应错误")
    private String sign;


}