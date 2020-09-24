package com.r7.core.setting.common.domain;


import java.lang.annotation.*;


/**
 * @author jinghan
 * @title: YmLog
 * @projectName mobilepay
 * @description: 云觅日志记录
 * @date 2020/4/2 10:52
 *
 * 这里@Target(ElementType.METHOD)标记表示我们此处的自定义注解应用在方法上。当然，还有其他的类型：
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface YmLog {
    String value() default "";
}
