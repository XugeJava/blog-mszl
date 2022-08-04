package com.xuge.mszl.service;

import com.xuge.mszl.bean.SysUser;
import com.xuge.mszl.bean.vo.UserVo;
import com.xuge.mszl.common.Result;

/**
 * author: yjx
 * Date :2022/7/2813:55
 **/
public interface SysUserService {
  SysUser findSysUserById(Long authorId);

  SysUser findUser(String account, String password);

  Result findUserByToken(String token);

  SysUser findUserByAccount(String account);

  void insert(SysUser sysUser);
  UserVo findUserVoById(Long id);
}
