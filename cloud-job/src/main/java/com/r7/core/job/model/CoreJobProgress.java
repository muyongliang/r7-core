package com.r7.core.job.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.job.dto.CoreJobProgressDto;
import com.r7.core.job.vo.CoreJobProgressVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 任务进度实体
 * @author zs
 */
@Data
@Table(name="core_job_progress")
@ApiModel(description = "任务进度明细")
public class CoreJobProgress extends Model<CoreJobProgress> {
    /** id */
    @ApiModelProperty(value = "id")
    private Long id ;
    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private Long userId ;
    /** 任务标识 */
    @ApiModelProperty(value = "任务标识")
    private String jobCode ;
    /** 任务执行状态;-1执行失败；1待执行；2执行中；3执行成功 */
    @ApiModelProperty(value = "任务执行状态", example = "-1执行失败；1待执行；2执行中；3执行成功")
    private Integer status ;
    /** 任务的进度json格式;如用户购买商品付款时调用接口进行记录判断（存在问题）json 存储用户任务完成记录，签到每天触发一次。认证是一个用户只能触发，根据规则判断任务的情况 */
    @ApiModelProperty(value = "任务进度")
    private String progress ;
    /** 是否发放;1否；2是 */
    @ApiModelProperty(value = "是否发放", example = "1否；2是")
    private Integer isDistribution ;
    /** 用户任务开始时间 */
    @ApiModelProperty(value = "用户任务开始时间")
    private Date startAt ;
    /** 用户任务完成时间 */
    @ApiModelProperty(value = "用户任务完成时间")
    private Date endAt ;
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    private Long createdBy ;
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createdAt ;
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    private Long updatedBy ;
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date updatedAt ;

    public void toCoreJobProgress(CoreJobProgressDto coreJobProgressDto) {
        this.setUserId(coreJobProgressDto.getUserId());
        this.setJobCode(coreJobProgressDto.getJobCode());
        this.setStatus(coreJobProgressDto.getStatus());
        this.setProgress(coreJobProgressDto.getProgress());
        this.setIsDistribution(coreJobProgressDto.getIsDistribution());
    }

    public CoreJobProgressVo toCoreJobProgressVo() {
        CoreJobProgressVo coreJobProgressVo = new CoreJobProgressVo();
        coreJobProgressVo.setUserId(this.getUserId());
        coreJobProgressVo.setJobCode(this.getJobCode());
        coreJobProgressVo.setStatus(this.getStatus());
        coreJobProgressVo.setProgress(this.getProgress());
        coreJobProgressVo.setIsDistribution(this.getIsDistribution());
        coreJobProgressVo.setStartAt(this.getStartAt());
        coreJobProgressVo.setEndAt(this.getEndAt());
        return coreJobProgressVo;
    }
}