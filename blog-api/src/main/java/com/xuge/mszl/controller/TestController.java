package com.xuge.mszl.controller;

import com.xuge.mszl.common.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: yjx
 * Date :2022/8/115:26
 **/
@RestController
@RequestMapping("test")
@CrossOrigin
public class TestController {

  @RequestMapping
  public Result test(){
    return Result.success(null);
  }
}
