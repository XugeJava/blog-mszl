package com.xuge.mszl.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuge.mszl.bean.SysUser;
import com.xuge.mszl.bean.vo.LoginUserVo;
import com.xuge.mszl.bean.vo.UserVo;
import com.xuge.mszl.common.ErrorCode;
import com.xuge.mszl.common.Result;
import com.xuge.mszl.mapper.SysMapper;
import com.xuge.mszl.service.LoginService;
import com.xuge.mszl.service.SysUserService;
import com.xuge.mszl.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * author: yjx
 * Date :2022/7/2813:55
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysMapper,SysUser > implements SysUserService {
  @Autowired
  private LoginService  loginService;
  @Autowired
  private RedisTemplate redisTemplate;
  @Override
  public SysUser findSysUserById(Long authorId) {
    SysUser sysUser = baseMapper.selectById(authorId);
    //如果为空
    if(sysUser==null){
      //设置默认数据
      sysUser=new SysUser();
      //设置名称
      sysUser.setNickname("徐哥啊");
    }
    return sysUser;
  }
  //查询用户
  @Override
  public SysUser findUser(String account, String password) {
    QueryWrapper<SysUser> qw = new QueryWrapper<>();
    qw.eq("account",account);
    qw.eq("password",password);
    //只显示1条
    qw.last("limit 1");
    return baseMapper.selectOne(qw);
  }
 //根据token查询用户信息

  /**
   * 1.token合法性校验
   * 是否为空，解析是否为空，redis是否存在
   * 2.如果失败，返回失败结果
   * 3.如果成功，返回成功数据
   * @param token
   * @return
   */
  @Override
  public Result findUserByToken(String token) {
    //校验
    SysUser sysUser=loginService.checkToken(token);
    if(sysUser==null){
      return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());

    }
    //数据拼接
    LoginUserVo loginUserVo = new LoginUserVo();
    loginUserVo.setAccount(sysUser.getAccount());
    loginUserVo.setAvatar(sysUser.getAvatar());
    loginUserVo.setId(String.valueOf(sysUser.getId()));
    loginUserVo.setNickname(sysUser.getNickname());
    return  Result.success(loginUserVo);
  }

  /**
   * 根据账户查询用户信息
   * @param account
   * @return
   */
  @Override
  public SysUser findUserByAccount(String account) {
    QueryWrapper<SysUser> qw = new QueryWrapper<>();
    qw.eq("account",account);
    qw.last("limit  1");
    return baseMapper.selectOne(qw);
  }

  @Override
  public void insert(SysUser sysUser) {
    this.save(sysUser);
  }

  @Override
  public UserVo findUserVoById(Long id) {
    SysUser sysUser = baseMapper.selectById(id);
    if (sysUser == null){
      sysUser = new SysUser();
      sysUser.setId(1L);
      sysUser.setAvatar("/static/img/logo.b3a48c0.png");
      sysUser.setNickname("码神之路");
    }
    //设置vo对象数据
    UserVo userVo = new UserVo();
    //设置Id
    userVo.setId(String.valueOf(sysUser.getId()));
    userVo.setAvatar(sysUser.getAvatar());
    userVo.setNickname(sysUser.getNickname());
    userVo.setId(String.valueOf(sysUser.getId()));
    return userVo;
  }


}
