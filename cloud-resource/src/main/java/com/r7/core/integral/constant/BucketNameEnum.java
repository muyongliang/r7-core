package com.r7.core.integral.constant;

/**
 * @author muyongliang
 * @date 2020/10/13
 * @description BucketNameEnum minIO桶名;根据文件大小划分，枚举，small(0-1mb)，middle(1mb-10mb)，large(10mb-1gb)
 */
public enum BucketNameEnum {
    /**
     * small(0-1mb)
     */
    SMALL,
    /**
     * middle(1mb-10mb)
     */
    MIDDLE,
    /**
     * large(10mb-1gb)
     */
    LARGE,
    /**
     * 超过1gb
     */
    HUGE,
    ;

    BucketNameEnum() {
    }
}
