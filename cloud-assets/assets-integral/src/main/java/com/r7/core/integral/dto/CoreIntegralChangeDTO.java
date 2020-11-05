package com.r7.core.integral.dto;

import com.r7.core.integral.constant.SourceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wt
 * @Description 增加/减少积分传输层
 */
@Data
@ApiModel(value = "增加减少积分传输层")
public class CoreIntegralChangeDTO {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 变动积分数 变动积分数就是代表增加时增加了多少积分，使用时使用了多少积分
     */
    @NotNull(message = "变动积分数不能为空")
    @ApiModelProperty(value = "变动积分数 变动积分数就是代表增加时增加了多少积分，使用时使用了多少积分")
    private Integer changeNum;

    /**
     * 业务编号 多次调接口区分
     */
    @NotBlank(message = "业务编号不能为空")
    @ApiModelProperty(value = "业务编号 多次调接口区分")
    private String businessCode;

    /**
     * 积分来源类型 游戏1/完成系统任务2/权益转换积分3/现金购买积分4/用户注册成功赠送积分5/权益商品卖出6/
     */
    @NotNull(message = "积分来源类型不能为空")
    @ApiModelProperty(value = "积分来源类型 游戏1/完成系统任务2/权益转换积分3/现金购买积分4/用户注册成功赠送积分5/权益商品卖出6/")
    private SourceTypeEnum sourceType;

    /**
     * 积分描述
     */
    @NotBlank(message = "积分描述不能为空")
    @ApiModelProperty(value = "积分描述")
    private String description;


}
