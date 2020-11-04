package com.r7.core.stand.video.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
@TableName("core_video")
@ApiModel(description = "视频信息")
@EqualsAndHashCode(callSuper = true)
public class CoreVideo extends Model<CoreVideo> {
    /** id */
    @TableId
    @ApiModelProperty(value = "id")
    private Long id ;
    /** 平台ID */
    @ApiModelProperty(value = "平台id")
    private Long appId ;
    /** 被视频录制的用户id */
    @ApiModelProperty(value = "用户id")
    private Long userId ;
    /** 组织ID */
    @ApiModelProperty(value = "组织id")
    private Long organId ;
    /** 资源的URL */
    @ApiModelProperty(value = "资源的URL")
    private String url ;
    /** 本地的存放url（待定）;上传成功后删掉 */
    @ApiModelProperty(value = "本地的存放url（待定）;上传成功后删掉")
    private String locationUrl ;
    /** 供给方 */
    @ApiModelProperty(value = "供给方")
    private String supplySide ;
    /** 录制视频的描述 */
    @ApiModelProperty(value = "录制视频的描述")
    private String description ;
    /** 上传状态;上传成功1/上传失败2/上传中3 */
    @ApiModelProperty(value = "上传状态", example = "上传成功1/上传失败2/上传中3")
    private Integer status ;
    /** 上传失败原因 */
    @ApiModelProperty(value = "上传失败原因")
    private String failReason ;
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    private Long createdBy ;
    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    private Date createdAt ;
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    private Long updateBy ;
    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    private Date updateAt ;

    /** id */
    public Long getId(){
        return this.id;
    }
    /** id */
    public void setId(Long id){
        this.id = id;
    }
    /** 平台ID */
    public Long getAppId(){
        return this.appId;
    }
    /** 平台ID */
    public void setAppId(Long appId){
        this.appId = appId;
    }
    /** 被视频录制的用户id */
    public Long getUserId(){
        return this.userId;
    }
    /** 被视频录制的用户id */
    public void setUserId(Long userId){
        this.userId = userId;
    }
    /** 组织ID */
    public Long getOrganId(){
        return this.organId;
    }
    /** 组织ID */
    public void setOrganId(Long organId){
        this.organId = organId;
    }
    /** 资源的URL */
    public String getUrl(){
        return this.url;
    }
    /** 资源的URL */
    public void setUrl(String url){
        this.url = url;
    }
    /** 本地的存放url（待定）;上传成功后删掉 */
    public String getLocationUrl(){
        return this.locationUrl;
    }
    /** 本地的存放url（待定）;上传成功后删掉 */
    public void setLocationUrl(String locationUrl){
        this.locationUrl = locationUrl;
    }
    /** 供给方 */
    public String getSupplySide(){
        return this.supplySide;
    }
    /** 供给方 */
    public void setSupplySide(String supplySide){
        this.supplySide = supplySide;
    }
    /** 录制视频的描述 */
    public String getDescription(){
        return this.description;
    }
    /** 录制视频的描述 */
    public void setDescription(String description){
        this.description = description;
    }
    /** 上传状态;上传成功1/上传失败2/上传中3 */
    public Integer getStatus(){
        return this.status;
    }
    /** 上传状态;上传成功1/上传失败2/上传中3 */
    public void setStatus(Integer status){
        this.status = status;
    }
    /** 上传失败原因 */
    public String getFailReason(){
        return this.failReason;
    }
    /** 上传失败原因 */
    public void setFailReason(String failReason){
        this.failReason = failReason;
    }
    /** 创建人 */
    public Long getCreatedBy(){
        return this.createdBy;
    }
    /** 创建人 */
    public void setCreatedBy(Long createdBy){
        this.createdBy = createdBy;
    }
    /** 创建时间 */
    public Date getCreatedAt(){
        return this.createdAt;
    }
    /** 创建时间 */
    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }
    /** 更新人 */
    public Long getUpdateBy(){
        return this.updateBy;
    }
    /** 更新人 */
    public void setUpdateBy(Long updateBy){
        this.updateBy = updateBy;
    }
    /** 更新时间 */
    public Date getUpdateAt(){
        return this.updateAt;
    }
    /** 更新时间 */
    public void setUpdateAt(Date updateAt){
        this.updateAt = updateAt;
    }
}