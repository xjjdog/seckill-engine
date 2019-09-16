package com.github.xjjdog.seckill.demo.security.annotation;

import java.lang.annotation.*;

/**
 * 权限注解
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission {

    String taskId() default "";

    String userId() default "";
}
