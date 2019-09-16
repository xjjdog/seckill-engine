package com.github.xjjdog.seckill.demo.security.annotation;

import com.github.xjjdog.seckill.demo.security.common.Constant;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

    String value() default Constant.CURRENT_USER;
}
