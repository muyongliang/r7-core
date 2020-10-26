package com.r7.core.integral.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Auther muyongliang
 * @Date 2020/10/10
 * @Description FileDataVO
 */
@Data
@TableName("core_file")
@EqualsAndHashCode(callSuper = true)
public class CoreFileDO extends Model<CoreFileDO> {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * minIO桶名;根据文件大小划分，枚举，small(0-1mb)，middle(1mb-10mb)，large(10mb-1gb)
     */
    private String bucketName;
    /**
     * 文件名;文件MD5校验值,同时作为对象名
     */
    private String fileName;
    /**
     * aes秘钥
     */
    private String aesKey;
    /**
     * 原始文件名;文件上传时的原始文件名
     */
    private String originalFileName;
    /**
     * 文件后缀
     */
    private String extension;
    /**
     * 资源描述
     */
    private String description;
    /**
     * 状态;-1已删除（用户不可见）1已发布
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createdAt;
}
