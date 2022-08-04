package com.xuge.mszl.controller.common;

import com.xuge.mszl.common.Result;
import com.xuge.mszl.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author: yjx
 * Date :2022/8/114:14
 **/
@RestController
@RequestMapping("logout")
@CrossOrigin
public class LogoutController {
  @Autowired
  private LoginService loginService;
  @GetMapping
  public Result logout(@RequestHeader("Authorization")String token){
    return loginService.logout(token);
  }

}
