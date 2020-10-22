package com.r7.core.job.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 任务进度信息传输层
 * @author zs
 */
@Data
@ApiModel(description = "任务进度明细")
public class CoreJobProgressDTO {

    /** 任务标识 */
    @ApiModelProperty(value = "任务标识")
    @Length(min = 1, max = 32, message = "任务标识不能超过32位")
    @NotBlank(message = "任务标识不能为空")
    private String jobCode ;

    /** 任务执行状态;-1执行失败；1待执行；2执行中；3执行成功 */
    @ApiModelProperty(value = "任务执行状态", example = "-1执行失败；1待执行；2执行中；3执行成功")
    @Range(min = -1, max = 3, message = "-1执行失败；1待执行；2执行中；3执行成功")
    @NotNull(message = "任务执行状态不能为空，默认1")
    private Integer status ;

    /** 任务的进度json格式;如用户购买商品付款时调用接口进行记录判断（存在问题）json 存储用户任务完成记录，
     * 签到每天触发一次。认证是一个用户只能触发，根据规则判断任务的情况 */
    @ApiModelProperty(value = "任务进度")
    @NotBlank(message = "任务进度不能为空")
    private String progress ;

    /** 是否发放;1否；2是 */
    @ApiModelProperty(value = "是否发放", example = "1否；2是")
    @Range(min = 1, max = 2, message = "1否；2是")
    @NotNull(message = "是否发放不能为空，默认1")
    private Integer isDistribution ;
}