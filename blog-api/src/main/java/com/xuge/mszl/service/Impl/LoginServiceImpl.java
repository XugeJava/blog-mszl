package com.xuge.mszl.service.Impl;

import com.alibaba.fastjson.JSON;
import com.xuge.mszl.bean.SysUser;
import com.xuge.mszl.bean.params.LoginParam;
import com.xuge.mszl.common.ErrorCode;
import com.xuge.mszl.common.Result;
import com.xuge.mszl.service.LoginService;
import com.xuge.mszl.service.SysUserService;
import com.xuge.mszl.utils.JWTUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service

public class LoginServiceImpl implements LoginService {

  private static final String slat = "mszlu!@#";
  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  /**
   * 1.检查参数是否合法
   * 2.根据用户名和密码去user表中查询，是否存在
   * 3.如果不存在，登录失败
   * 4.如果存在，使用jwt，生成Token,返回给前端
   * 5.token放入redis中，redis 中  token: user 信息  设置过期时间
   */

  @Override
  public Result login(LoginParam loginParam) {
    String account = loginParam.getAccount();
    String password = loginParam.getPassword();
    if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
      return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
    }
    String pwd = DigestUtils.md5Hex(password + slat);
    SysUser sysUser = sysUserService.findUser(account, pwd);
    if (sysUser == null) {
      return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
    }
    //登录成功，使用JWT生成token，返回token和redis中
    String token = JWTUtils.createToken(sysUser.getId());
    redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
    return Result.success(token);
  }

  //校验token
  @Override
  public SysUser checkToken(String token) {
    if (StringUtils.isBlank(token)) {
      return null;
    }
    Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
    if (stringObjectMap == null) {
      return null;
    }
    //从redis中获取
    String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
    if (StringUtils.isBlank(userJson)) {
      return null;
    }
    SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
    return sysUser;
  }

  @Override
  public Result logout(String token) {
    //删除redis中的token
    redisTemplate.delete("TOKEN_"+token);
    return Result.success(null);
  }

  /**
   * 1.用户名是否合法
   * 2.账户是否存在
   * 3.不存在，注册用户，生成token
   * 4.存入redis，返回
   * 5.加上事务
   * @param loginParam
   * @return
   */
  @Override

  public Result register(LoginParam loginParam) {
    //1.校验用户参数信息
    String account = loginParam.getAccount();
    String password = loginParam.getPassword();
    String nickname = loginParam.getNickname();
    if (StringUtils.isBlank(account)
            || StringUtils.isBlank(password)
            || StringUtils.isBlank(nickname)
    ){
      return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
    }
    //2.账户是否存在
    SysUser sysUser = this.sysUserService.findUserByAccount(account);
    if (sysUser != null){
      return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
    }
    sysUser=new SysUser();
    //给sysUser设置值
    sysUser.setNickname(nickname);
    sysUser.setAccount(account);
    sysUser.setPassword(DigestUtils.md5Hex(password+slat));
    sysUser.setCreateDate(System.currentTimeMillis());
    sysUser.setLastLogin(System.currentTimeMillis());
    sysUser.setAvatar("/static/img/logo.b3a48c0.png");
    sysUser.setAdmin(1); //1 为true
    sysUser.setDeleted(0); // 0 为false
    sysUser.setSalt("");
    sysUser.setStatus("");
    sysUser.setEmail("");
    //执行插入=>注册实现
    sysUserService.insert(sysUser);
    //生成Token 存入redis
    String token = JWTUtils.createToken(sysUser.getId());

    redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
    return Result.success(token);
  }

}


