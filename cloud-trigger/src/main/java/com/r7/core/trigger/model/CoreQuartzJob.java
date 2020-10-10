package com.r7.core.trigger.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.r7.core.trigger.dto.CoreQuartzJobDTO;
import com.r7.core.trigger.dto.CoreQuartzJobUpdateDTO;
import com.r7.core.trigger.vo.CoreQuartzJobUpdateVO;
import com.r7.core.trigger.vo.CoreQuartzJobVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @Description 定时任务表
 * @author wutao
 * @date 2020/9/28
 */
@ApiModel(description="定时任务表")
@Data
@TableName(value = "core_quartz_job")
@EqualsAndHashCode(callSuper = true)
public class CoreQuartzJob extends Model<CoreQuartzJob> {
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
     * 任务路径
     */
    @ApiModelProperty(value="任务路径")
    private String jobClass;

    /**
     * 任务名称
     */
    @ApiModelProperty(value="任务名称")
    private String jobName;

    /**
     * 规则表达式
     */
    @ApiModelProperty(value="规则表达式")
    private String ruleExpression;

    /**
     * 错失策略 1下周期再执行/2错失周期执行一次/3错失周期立即执行
     */
    @ApiModelProperty(value="错失策略 1下周期再执行/2错失周期执行一次/3错失周期立即执行")
    private Integer misfire;

    /**
     * 任务状态（启动/暂停） -1异常1待启动2正常3暂停
     */
    @ApiModelProperty(value="任务状态（启动/暂停） -1异常1待启动2正常执行3暂停")
    private Integer status;

    /**
     * 执行次数
     */
    @ApiModelProperty(value="执行次数")
    private Integer count;

    /**
     * 出错次数
     */
    @ApiModelProperty(value="出错次数")
    private Integer errorsNum;

    /**
     * 启动时间
     */
    @ApiModelProperty(value="启动时间")
    private LocalDateTime startAt;

    /**
     * 恢复时间
     */
    @ApiModelProperty(value="恢复时间")
    private LocalDateTime regainAt;

    /**
     * 分组
     */
    @ApiModelProperty(value="分组")
    private String jobGroup;

    /**
     * 任务描述
     */
    @ApiModelProperty(value="描述")
    private String description;

    /**
     * 创建人
     */
    @ApiModelProperty(value="创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createdAt;

    /**
     * 更新人
     */
    @ApiModelProperty(value="更新人")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private LocalDateTime updatedAt;

    public void toCoreQuartzJobUpdateDto(CoreQuartzJobUpdateDTO coreQuartzJobUpdateDto){
        this.setDescription(coreQuartzJobUpdateDto.getDescription());
        this.setRuleExpression(coreQuartzJobUpdateDto.getRuleExpression());
    }

    public CoreQuartzJobUpdateVO toCoreQuartzJobUpdateVo(){
        CoreQuartzJobUpdateVO coreQuartzJobUpdateVo = new CoreQuartzJobUpdateVO();
        coreQuartzJobUpdateVo.setDescription(this.description);
        coreQuartzJobUpdateVo.setRuleExpression(this.ruleExpression);
        coreQuartzJobUpdateVo.setId(this.getId());
        return coreQuartzJobUpdateVo;

    }


    public void toCoreQuartzJobDto(CoreQuartzJobDTO coreQuartzJobDto){

        this.setAppId(coreQuartzJobDto.getAppId());
        this.setJobName(coreQuartzJobDto.getJobName());
        this.setJobGroup(coreQuartzJobDto.getJobGroup());
        this.setJobClass(coreQuartzJobDto.getJobClass());
        this.setMisfire(coreQuartzJobDto.getMisfire());
        this.setStatus(coreQuartzJobDto.getStatus());
        this.setRuleExpression(coreQuartzJobDto.getRuleExpression());
        this.setDescription(coreQuartzJobDto.getDescription());

    }
    public CoreQuartzJobVO toCoreQuartzJobVo(){
        CoreQuartzJobVO coreQuartzJobVo = new CoreQuartzJobVO();
        coreQuartzJobVo.setAppId(this.appId);
        coreQuartzJobVo.setId(this.id);
        coreQuartzJobVo.setJobClass(this.jobClass);
        coreQuartzJobVo.setJobGroup(this.jobGroup);
        coreQuartzJobVo.setJobName(this.jobName);
        coreQuartzJobVo.setMisfire(this.misfire);
        coreQuartzJobVo.setRuleExpression(this.ruleExpression);
        coreQuartzJobVo.setStatus(this.status);
        coreQuartzJobVo.setRegainAt(this.regainAt);
        coreQuartzJobVo.setStartAt(this.startAt);
        coreQuartzJobVo.setDescription(this.description);
        return coreQuartzJobVo;
    }
}