package com.r7.core.integral.dto;

import lombok.Data;

/**
 * @Auther muyongliang
 * @Date 2020/10/10
 * @Description FileDataVO
 */
@Data
public class CoreFileDTO {


    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件是否已经存在
     */
    private boolean Exist;
    /**
     * 文件原始名
     */
    private boolean originalFileName;
}
