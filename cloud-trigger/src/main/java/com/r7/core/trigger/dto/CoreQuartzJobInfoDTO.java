package com.r7.core.trigger.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author wutao
 * @Description 定时任务执行情况传输层
 * @date 2020/9/28
 */
@Data
@ApiModel(description = "定时任务执行情况传输层")
public class CoreQuartzJobInfoDTO {
    /**
     * id
     */
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 平台id
     */
    @NotNull(message = "平台id不能为空")
    @ApiModelProperty(value="平台id")
    private Long appId;

    /**
     * 平台id
     */
    @NotNull(message = "任务id不能为空")
    @ApiModelProperty(value="任务id")
    private Long jobId;

    /**
     * 状态 -1异常1未启动2正常3正在执行4暂停
     * @NotNull(message = "状态不能为空")
     *@ApiModelProperty(value="状态 -1异常1未启动2正常3正在执行4暂停")
     *  */
     private Integer status;



    /**
     * 任务执行情况描述
     */
    @ApiModelProperty(value="任务执行情况描述")
    private String description;

}
