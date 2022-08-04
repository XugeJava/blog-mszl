package com.xuge.mszl.common.cache;

import java.lang.annotation.*;

/**
 * author: yjx
 * Date :2022/8/313:39
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

  long expire() default 1 * 60 * 1000;

  String name() default "";

}
