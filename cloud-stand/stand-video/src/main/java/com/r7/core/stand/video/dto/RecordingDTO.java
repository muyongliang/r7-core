package com.r7.core.stand.video.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zs
 * @description:
 * @date : 2020-10-20
 */
@Data
@ApiModel("视频录制传输实体")
public class RecordingDTO {

    @ApiModelProperty("用户id")
    private Integer uid;

    @ApiModelProperty("平台id")
    private String appId;

    @ApiModelProperty("频道")
    private String channel;

    @ApiModelProperty("创建频道时生成的token")
    private String channelKey;
}
