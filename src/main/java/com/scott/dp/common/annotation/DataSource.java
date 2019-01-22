package com.scott.dp.common.annotation;

import java.lang.annotation.*;

/**
 * 数据源注解
 * @author Mr.薛
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    String value() default "";

}
