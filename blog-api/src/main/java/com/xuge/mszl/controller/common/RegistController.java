package com.xuge.mszl.controller.common;

import com.xuge.mszl.bean.params.LoginParam;
import com.xuge.mszl.common.Result;
import com.xuge.mszl.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author: yjx
 * Date :2022/8/114:20
 **/
@RestController
@RequestMapping("register")
@CrossOrigin
public class RegistController {
  @Autowired
  private LoginService loginService;
  @PostMapping
  public Result register(@RequestBody LoginParam loginParam){
     return  loginService.register(loginParam);
  }

}
