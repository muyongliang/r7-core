package com.r7.core.job.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


@ApiModel(description = "任务类")
public class CoreJobDto {
    /** id;任务id */
//    @ApiModelProperty(value = "id")
//    private Long id ;
//
//    @ApiModelProperty(value = "平台Id", example = "股票1/商城2/游戏3")
//    private Long appId ;

    @ApiModelProperty(value = "任务名称")
    private String jobName ;

    @ApiModelProperty(value = "任务标识", example = "用于展示")
    private String jobCode ;

    @ApiModelProperty(value = "任务内容")
    private String content ;

    @ApiModelProperty(value = "任务规则")
    private String jobRule ;

    @ApiModelProperty(value = "任务状态", example = "上架1/未上架2")
    private Integer status ;

//    @ApiModelProperty(value = "任务上架时间")
//    private Date onShelf ;
//
//    @ApiModelProperty(value = "任务下架时间")
//    private Date offShelf ;

    @ApiModelProperty(value = "创建人")
    private Long createdBy ;

    @ApiModelProperty(value = "更新人")
    private Long updatedBy ;


//    /** id;任务id */
//    public Long getId(){
//        return this.id;
//    }
//    /** id;任务id */
//    public void setId(Long id){
//        this.id = id;
//    }
//    /** 平台id;股票1/商城2/游戏3 */
//    public Long getAppId(){
//        return this.appId;
//    }
//    /** 平台id;股票1/商城2/游戏3 */
//    public void setAppId(Long appId){
//        this.appId = appId;
//    }
    /** 任务名称 */
    public String getJobName(){
        return this.jobName;
    }
    /** 任务名称 */
    public void setJobName(String jobName){
        this.jobName = jobName;
    }
    /** 任务标识;展示用 */
    public String getJobCode(){
        return this.jobCode;
    }
    /** 任务标识;展示用 */
    public void setJobCode(String jobCode){
        this.jobCode = jobCode;
    }
    /** 任务内容;任务的内容 */
    public String getContent(){
        return this.content;
    }
    /** 任务内容;任务的内容 */
    public void setContent(String content){
        this.content = content;
    }
    /** 任务规则;存json：任务奖励金额/积分，任务目标 */
    public String getJobRule(){
        return this.jobRule;
    }
    /** 任务规则;存json：任务奖励金额/积分，任务目标 */
    public void setJobRule(String jobRule){
        this.jobRule = jobRule;
    }
//    /** 任务完成人数 */
//    public Integer getWinnerNum(){
//        return this.winnerNum;
//    }
//    /** 任务完成人数 */
//    public void setWinnerNum(Integer winnerNum){
//        this.winnerNum = winnerNum;
//    }
    /** 任务状态;上架1/未上架2 */
    public Integer getStatus(){
        return this.status;
    }
    /** 任务状态;上架1/未上架2 */
    public void setStatus(Integer status){
        this.status = status;
    }
//    /** 上架时间;任务的上架时间 */
//    public Date getOnShelf(){
//        return this.onShelf;
//    }
//    /** 上架时间;任务的上架时间 */
//    public void setOnShelf(Date onShelf){
//        this.onShelf = onShelf;
//    }
//    /** 下架时间 */
//    public Date getOffShelf(){
//        return this.offShelf;
//    }
//    /** 下架时间 */
//    public void setOffShelf(Date offShelf){
//        this.offShelf = offShelf;
//    }
    /** 创建人 */
    public Long getCreatedBy(){
        return this.createdBy;
    }
    /** 创建人 */
    public void setCreatedBy(Long createdBy){
        this.createdBy = createdBy;
    }
//    /** 创建时间 */
//    public Date getCreatedAt(){
//        return this.createdAt;
//    }
//    /** 创建时间 */
//    public void setCreatedAt(Date createdAt){
//        this.createdAt = createdAt;
//    }
    /** 更新人 */
    public Long getUpdatedBy(){
        return this.updatedBy;
    }
    /** 更新人 */
    public void setUpdatedBy(Long updatedBy){
        this.updatedBy = updatedBy;
    }
//    /** 更新时间 */
//    public Date getUpdatedAt(){
//        return this.updatedAt;
//    }
//    /** 更新时间 */
//    public void setUpdatedAt(Date updatedAt){
//        this.updatedAt = updatedAt;
//    }
}