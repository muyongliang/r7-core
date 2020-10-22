package com.r7.core.trigger.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.trigger.dto.CoreQuartzJobInfoDTO;
import com.r7.core.trigger.vo.CoreQuartzJobInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @Description 定时任务执行情况
 * @author wutao
 * @date 2020/9/28
 */
@ApiModel(value="定时任务执行情况")
@Data
@TableName(value = "core_quartz_job_info")
@EqualsAndHashCode(callSuper = true)
public class CoreQuartzJobInfo extends Model<CoreQuartzJobInfo> {
    /**
     * id
     */
    @TableId(value = "id")
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 平台id
     */
    @ApiModelProperty(value="平台id")
    private Long appId;

    /**
     * 平台id
     */
    @ApiModelProperty(value="任务id")
    private Long jobId;


    /**
     * 任务执行情况描述
     */
    @ApiModelProperty(value="任务执行情况描述")
    private String description;

    /**
     * 任务状态（启动/暂停） -1异常1待启动2正常3暂停
     */
    @ApiModelProperty(value="任务状态（启动/暂停） -1异常1待启动2正常执行3暂停")
    private Integer status;


    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createdAt;


    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private LocalDateTime updatedAt;

    /**
     * 作业执行开始时间
     */
    @ApiModelProperty(value="作业执行开始时间")
    private LocalDateTime startAt;
    /**
     * 作业执行结束时间
     */
    @ApiModelProperty(value="作业执行结束时间")
    private LocalDateTime endAt;

    public void toCoreQuartzJobInfoDto(CoreQuartzJobInfoDTO coreQuartzJobInfoDto){
        this.setId(coreQuartzJobInfoDto.getId());
        this.setAppId(coreQuartzJobInfoDto.getAppId());
        this.setJobId(coreQuartzJobInfoDto.getJobId());
        this.setDescription(coreQuartzJobInfoDto.getDescription());
        this.setStatus(coreQuartzJobInfoDto.getStatus());
    }

    public CoreQuartzJobInfoVO toCoreQuartzJobInfoVo(){
        CoreQuartzJobInfoVO coreQuartzJobInfoVo = new CoreQuartzJobInfoVO();
        coreQuartzJobInfoVo.setId(this.id);
        coreQuartzJobInfoVo.setAppId(this.appId);
        coreQuartzJobInfoVo.setJobId(this.jobId);
        coreQuartzJobInfoVo.setDescription(this.description);
        coreQuartzJobInfoVo.setCreatedAt(this.createdAt);
        coreQuartzJobInfoVo.setStatus(this.status);
        coreQuartzJobInfoVo.setStartAt(this.startAt);
        coreQuartzJobInfoVo.setEndAt(this.endAt);
        return coreQuartzJobInfoVo;
    }
}