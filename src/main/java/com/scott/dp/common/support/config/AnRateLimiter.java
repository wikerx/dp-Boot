package com.scott.dp.common.support.config;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @InterfaceName :AnRateLimiter
 * @Description :自定义注解
 * @Author :Mr.薛
 * @Data :2019/11/15  10:03
 * @Version :V1.0
 * @Status : 编写
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AnRateLimiter {
    //以固定数值往令牌桶添加令牌
    double permitsPerSecond() default 5;

    //获取令牌最大等待时间
    long timeout();

    // 单位(例:分钟/秒/毫秒) 默认:毫秒
//    TimeUnit timeunit() default TimeUnit.MILLISECONDS;
    TimeUnit timeunit() default TimeUnit.SECONDS;

    // 无法获取令牌返回提示信息 默认值可以自行修改
    String msg() default "The system is busy. Please try again later.";
}
