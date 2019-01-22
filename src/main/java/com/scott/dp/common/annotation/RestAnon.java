package com.scott.dp.common.annotation;

import java.lang.annotation.*;

/**
 * rest接口不需要授权注解
 * @author Mr.薛
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestAnon {
}
