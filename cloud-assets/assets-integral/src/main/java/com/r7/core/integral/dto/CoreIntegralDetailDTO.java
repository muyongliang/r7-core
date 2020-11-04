package com.r7.core.integral.dto;

import com.r7.core.integral.constant.OperateTypeEnum;
import com.r7.core.integral.constant.SourceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @Description 积分详情传输层
 * @author wt
 * 
 */
@Data
@ApiModel(value="积分详情传输层")
public class CoreIntegralDetailDTO {


    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
     * 积分来源类型 游戏1/完成系统任务2/权益转换积分3/现金购买积分4/用户注册成功赠送积分5/权益商品卖出6/
     */
    @NotNull(message = "积分来源类型不能为空")
    @ApiModelProperty(value="积分来源类型 游戏1/完成系统任务2/权益转换积分3/现金购买积分4/用户注册成功赠送积分5/权益商品卖出6/")
    private SourceTypeEnum sourceType;

    /**
     * 业务编号 多次调接口区分
     */
    @NotBlank(message = "业务编号不能为空")
    @ApiModelProperty(value="业务编号 多次调接口区分")
    private String businessCode;

    /**
     * 当前积分值
     */
    @NotNull(message = "当前积分值不能为空")
    @ApiModelProperty(value="当前积分值")
    private Integer laveNum;

    /**
     * 变动积分数 变动积分数就是代表增加时增加了多少积分，使用时使用了多少积分
     */
    @NotNull(message = "变动积分数不能为空")
    @ApiModelProperty(value="变动积分数 变动积分数就是代表增加时增加了多少积分，使用时使用了多少积分")
    private Integer changeNum;

    /**
     * 操作类型 增加1/减少2
     */
    @NotNull(message = "操作类型不能为空")
    @ApiModelProperty(value="操作类型 增加1/减少2")
    private OperateTypeEnum operateType;

    /**
     * 日期 yyyyMMdd
     */
    @NotNull(message = "日期不能为空")
    @ApiModelProperty(value="日期 yyyyMMdd")
    private Integer detailedDate;

    /**
     * 积分描述
     */
    @NotBlank(message = "积分描述不能为空")
    @ApiModelProperty(value="积分描述")
    private String description;


    public  void toCoreIntegralChangeDTO(CoreIntegralChangeDTO coreIntegralChangeDTO){

        this.setUserId(coreIntegralChangeDTO.getUserId());
        this.setBusinessCode(coreIntegralChangeDTO.getBusinessCode());
        this.setChangeNum(coreIntegralChangeDTO.getChangeNum());
        this.setDescription(coreIntegralChangeDTO.getDescription());
        this.setSourceType(coreIntegralChangeDTO.getSourceType());
    }

}