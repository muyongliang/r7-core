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
     * 桶名，不传则用默认桶名defaultBucket
     */
    @ApiModelProperty(value = "桶名", notes = "桶名，不传则用默认桶名defaultBucket,名称只能包含小写字母，数字，点和连字符（-）")
    private String bucketName;
    /**
     * 文件MD5校验值
     */
    @ApiModelProperty(value = "文件MD5校验值,同时作为对象名", required = true)
    @NotEmpty(message = "文件MD5校验值不能为空")
    private String MD5;
    /**
     * 文件aes加密秘钥，经base64编码
     */
    @ApiModelProperty(value = "文件aes加密秘钥，经base64编码", notes = "使用加密保存的对象必须提供秘钥", required = true)
    private String aesKey;
    /**
     * 上传或者下载用时，毫秒数
     */
    @ApiModelProperty("上传或者下载用时,毫秒数")
    private Long usedTime;
    /**
     * 文件是否已经存在
     */
    private boolean Exist;
    /**
     * 其他信息
     */
    @ApiModelProperty("其他信息")
    private String msg;
}
