package com.r7.core.job.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.job.dto.CoreJobDto;
import com.r7.core.job.vo.CoreJobVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 任务实体
 * @author zs
 */
@Data
@TableName("core_job")
@ApiModel(description = "任务信息")
public class CoreJob extends Model<CoreJob> {
    /** id;任务id */
//    @Id
//    @GeneratedValue
    @ApiModelProperty(value = "id")
    private Long id ;

    @ApiModelProperty(value = "平台Id", example = "股票1/商城2/游戏3")
    private Long appId ;

    @ApiModelProperty(value = "任务名称")
    private String jobName ;

    @ApiModelProperty(value = "任务标识", example = "用于展示")
    private String jobCode ;

    @ApiModelProperty(value = "任务内容")
    private String content ;

    @ApiModelProperty(value = "任务规则")
    private String jobRule ;

    @ApiModelProperty(value = "任务完成人数")
    private Integer winnerNum ;

    @ApiModelProperty(value = "任务状态", example = "上架1/未上架2")
    private Integer status ;

    @ApiModelProperty(value = "任务上架时间", hidden = true)
    private Date onShelf ;

    @ApiModelProperty(value = "任务下架时间", hidden = true)
    private Date offShelf ;

    @ApiModelProperty(value = "创建人", hidden = true)
    private Long createdBy ;

    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date createdAt ;

    @ApiModelProperty(value = "更新人", hidden = true)
    private Long updatedBy ;

    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updatedAt ;

    public CoreJobVo toCoreJobVo() {
        CoreJobVo coreJobVo = new CoreJobVo();
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

    public void toCoreJob(CoreJobDto coreJobDto) {
        this.setJobName(coreJobDto.getJobName());
        this.setJobCode(coreJobDto.getJobCode());
        this.setContent(coreJobDto.getContent());
        this.setJobRule(coreJobDto.getJobRule());
        this.setStatus(coreJobDto.getStatus());
        this.setOnShelf(coreJobDto.getOnShelf());
    }
}