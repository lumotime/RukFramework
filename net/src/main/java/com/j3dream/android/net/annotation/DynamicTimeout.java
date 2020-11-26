package com.j3dream.android.net.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * <p>文件名称: DynamicTimeout </p>
 * <p>所属包名: com.j3dream.android.net.annotation</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/26 14:37 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DynamicTimeout {

    /**
     * 连接超时
     *
     * @return 连接超时时间, 使用 timeUnit 作为单位
     */
    int connectTimeout() default -1;

    /**
     * 读取超时
     *
     * @return 读取超时时间, 使用 timeUnit 作为单位
     */
    int readTimeout() default -1;

    /**
     * 写入超时
     *
     * @return 写入超时时间, 使用 timeUnit 作为单位
     */
    int writeTimeout() default -1;

    /**
     * 时间单位
     *
     * @return 超时时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
