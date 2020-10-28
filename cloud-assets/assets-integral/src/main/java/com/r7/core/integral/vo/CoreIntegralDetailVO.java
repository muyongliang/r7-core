package com.r7.core.integral.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @Description 积分详情视图
 * @author wt
 * 
 */
@Data
@ApiModel(value="积分详情视图")
public class CoreIntegralDetailVO {


    /**
     * 平台ID
     */
    @ApiModelProperty(value="平台ID")
    private Long appId;

    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
     * 积分来源类型 游戏1/完成系统任务2/权益转换积分3/现金购买积分4/用户注册成功赠送积分5/权益商品卖出6/
     */
    @ApiModelProperty(value="积分来源类型 游戏1/完成系统任务2/权益转换积分3/现金购买积分4/用户注册成功赠送积分5/权益商品卖出6/")
    private Integer sourceType;

    /**
     * 业务编号 多次调接口区分
     */
    @ApiModelProperty(value="业务编号 多次调接口区分")
    private String businessCode;

    /**
     * 当前积分值
     */
    @ApiModelProperty(value="当前积分值")
    private Integer laveNum;

    /**
     * 变动积分数 变动积分数就是代表增加时增加了多少积分，使用时使用了多少积分
     */
    @ApiModelProperty(value="变动积分数 变动积分数就是代表增加时增加了多少积分，使用时使用了多少积分")
    private Integer changeNum;

    /**
     * 操作类型 增加1/减少2
     */
    @ApiModelProperty(value="操作类型 增加1/减少2")
    private Integer operateType;

    /**
     * 日期 yyyyMMdd
     */
    @ApiModelProperty(value="日期 yyyyMMdd")
    private Integer detailedDate;

    /**
     * 积分描述
     */
    @ApiModelProperty(value="积分描述")
    private String description;


}