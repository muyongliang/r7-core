package com.r7.core.integral.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Auther muyongliang
 * @Date 2020/10/10
 * @Description FileDataVO
 */
@ApiModel(description = "文件元信息")
@Data
public class FileDataDTO {
    /**
     * fileName
     */
    @ApiModelProperty("fileName")
    private String fileName;
    /**
     * 桶名，不传则用默认桶名defaultBucket
     */
    @ApiModelProperty(value = "桶名")
    private String bucketName;
    /**
     * 文件MD5校验值
     */
    @ApiModelProperty(value = "文件MD5校验值", required = true)
    @NotEmpty(message = "文件MD5校验值不能为空")
    private String MD5;
    /**
     * 文件aes加密秘钥，经base64编码
     */
    @ApiModelProperty(value = "文件aes加密秘钥，经base64编码", required = true)
    @NotEmpty(message = "文件aesKey不能为空")
    private String aesKey;
    /**
     * 文件是否已经存在
     */
    private boolean Exist;
    /**
     * 文件新建时间
     */
    @ApiModelProperty("文件新建时间")
    private String createTime;
    /**
     * 文件修改时间
     */
    @ApiModelProperty("文件修改时间")
    private String updateTime;
}
