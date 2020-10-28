package com.r7.core.integral.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther muyongliang
 * @Date 2020/10/10
 * @Description FileDataVO
 */
@Data
@ApiModel("文件上传结果信息")
public class FileUploadVO {


    /**
     * 文件名
     */
    @ApiModelProperty("上传后生成的文件名")
    private String fileName;
    /**
     * 文件是否已经存在
     */
    @ApiModelProperty("文件是否已经上传过")
    private Boolean exist;
}
