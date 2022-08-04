package com.xuge.mszl.handler;

import com.xuge.mszl.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author: yjx
 * Date :2022/7/299:58
 **/
@ControllerAdvice

public class AllExceptionHandler {
  //进行异常处理
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public Result doException(Exception e){
    e.printStackTrace();
    return Result.fail(-999,"系统异常，程序员正在加急修复..");
  }

}
