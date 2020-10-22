package com.r7.core.job.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.job.dto.CoreJobDTO;
import com.r7.core.job.dto.CoreJobStatusDTO;
import com.r7.core.job.vo.CoreJobVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 任务实体
 * @author zs
 */
@Data
@TableName("core_job")
@ApiModel(description = "任务信息")
@EqualsAndHashCode(callSuper = true)
public class CoreJob extends Model<CoreJob> {
    /** id;任务id */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id;
    /**
     * 平台id
     */
    @ApiModelProperty(value = "平台Id", example = "股票1/商城2/游戏3")
    private Long appId;
    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")
    private String jobName;
    /**
     * 任务标识
     */
    @ApiModelProperty(value = "任务标识", example = "用于展示")
    private String jobCode;
    /**
     * 任务内容
     */
    @ApiModelProperty(value = "任务内容")
    private String content;
    /**
     * 任务规则
     */
    @ApiModelProperty(value = "任务规则")
    private String jobRule;
    /**
     * 任务完成人数
     */
    @ApiModelProperty(value = "任务完成人数")
    private Integer winnerNum;
    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态", example = "上架1/未上架2")
    private Integer status;
    /**
     * 任务上架时间
     */
    @ApiModelProperty(value = "任务上架时间", hidden = true)
    private Date onShelf;
    /**
     * 任务下架时间
     */
    @ApiModelProperty(value = "任务下架时间", hidden = true)
    private Date offShelf;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", hidden = true)
    private Long createdBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createdAt;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", hidden = true)
    private Long updatedBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updatedAt;

    public CoreJobVO toCoreJobVo() {
        CoreJobVO coreJobVo = new CoreJobVO();
        coreJobVo.setId(this.getId());
        coreJobVo.setAppId(this.getAppId());
        coreJobVo.setJobName(this.getJobName());
        coreJobVo.setJobCode(this.getJobCode());
        coreJobVo.setJobRule(this.getJobRule());
        coreJobVo.setContent(this.getContent());
        coreJobVo.setOnShelf(this.getOnShelf());
        coreJobVo.setOffShelf(this.getOffShelf());
        coreJobVo.setStatus(this.getStatus());
        coreJobVo.setWinnerNum(this.getWinnerNum());
        return coreJobVo;
    }

    public void toCoreJob(CoreJobDTO coreJobDto) {
        this.setJobName(coreJobDto.getJobName());
        this.setJobCode(coreJobDto.getJobCode());
        this.setContent(coreJobDto.getContent());
        this.setWinnerNum(coreJobDto.getWinnerNum());
        this.setJobRule(coreJobDto.getJobRule());
        this.setStatus(coreJobDto.getStatus());
        this.setOnShelf(coreJobDto.getOnShelf());
    }


    public void toCoreJobStatusVo(CoreJobStatusDTO coreJobStatusDto) {
        this.setStatus(coreJobStatusDto.getStatus());
    }
}