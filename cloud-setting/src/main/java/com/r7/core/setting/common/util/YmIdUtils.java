package com.r7.core.setting.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * @author jinghan
 * @title: IdUtils
 * @projectName mobilepay
 * @description: 分布式id 生成
 * @date 2020/8/27 10:23
 */
public class YmIdUtils {


    /**
     * //参数1为终端ID
     * //参数2为数据中心ID
     */
    static Snowflake snowflake = IdUtil.getSnowflake(RandomUtil.randomInt(29), RandomUtil.randomInt(29));


    /**
     * 分布式系统中，有一些需要使用全局唯一ID的场景，有些时候我们希望能使用一种简单一些的ID，并且希望ID能够按照时间有序生成。Twitter的Snowflake 算法就是这种生成器
     * 全局唯一ID
     *
     * @return
     */
    public static Long getSnowflakeId() {
        return snowflake.nextId() + RandomUtil.randomInt(0,9);
    }

    public static void main(String[] args) {
        System.out.println(getSnowflakeId().toString());
    }
}
