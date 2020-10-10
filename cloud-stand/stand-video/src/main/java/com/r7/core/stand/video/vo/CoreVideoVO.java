package com.r7.core.stand.video.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zs
 * @description:
 * @date : 2020-10-10
 */
@Data
@ApiModel(description = "视频类")
public class CoreVideoVO {
    /** id */
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

}
