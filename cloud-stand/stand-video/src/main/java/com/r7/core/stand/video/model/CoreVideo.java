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

}