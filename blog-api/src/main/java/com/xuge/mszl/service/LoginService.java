package com.xuge.mszl.service;

import com.xuge.mszl.bean.SysUser;
import com.xuge.mszl.bean.params.LoginParam;
import com.xuge.mszl.common.Result;
import org.springframework.transaction.annotation.Transactional;

/**
 * author: yjx
 * Date :2022/8/111:01
 **/
@Transactional
public interface LoginService {
  //登录方法
  Result login(LoginParam loginParam);

  //根据token返回用户信息并校验token
  SysUser checkToken(String token);

  //退出方法实现
  Result logout(String token);
  //注册实现
  Result register(LoginParam loginParam);
}
