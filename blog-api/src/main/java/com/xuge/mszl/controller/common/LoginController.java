package com.xuge.mszl.controller.common;

import com.xuge.mszl.bean.params.LoginParam;
import com.xuge.mszl.common.Result;
import com.xuge.mszl.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author: yjx
 * Date :2022/8/110:59
 **/
@RestController
@RequestMapping("login")
@CrossOrigin
public class LoginController {
  @Autowired
  private LoginService loginService;
  @PostMapping
  public Result login(@RequestBody LoginParam loginParam){
   return loginService.login(loginParam);
  }

}
