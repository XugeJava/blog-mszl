package com.xuge.mszl.common.aop;

import java.lang.annotation.*;

/**
 * author: yjx
 * Date :2022/8/39:41
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

  String module() default "";

  String operation() default "";
}
