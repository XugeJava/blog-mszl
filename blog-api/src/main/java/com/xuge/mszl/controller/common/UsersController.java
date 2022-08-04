package com.xuge.mszl.controller.common;

import com.xuge.mszl.bean.SysUser;
import com.xuge.mszl.common.Result;
import com.xuge.mszl.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author: yjx
 * Date :2022/8/111:43
 **/
@RestController
@RequestMapping("users")
@CrossOrigin
public class UsersController {
  @Autowired
  private SysUserService sysUserService;
  @GetMapping("currentUser")
  public Result currentUser(@RequestHeader("Authorization")String token){
    return sysUserService.findUserByToken(token);
  }
}
