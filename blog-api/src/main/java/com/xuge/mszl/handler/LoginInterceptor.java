package com.xuge.mszl.handler;

import com.alibaba.fastjson.JSON;
import com.xuge.mszl.bean.SysUser;
import com.xuge.mszl.common.ErrorCode;
import com.xuge.mszl.common.Result;
import com.xuge.mszl.service.LoginService;
import com.xuge.mszl.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * author: yjx
 * Date :2022/8/115:08
 **/
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
  @Autowired
  private LoginService loginService;
  /**
   * 1.需要判断 请求 接口路径是否为HandlerMethod 类型
   * 2.判断token是否为空，如果为空，未登陆
   * 3.如果token不为空，验证token信息
   * 4.认证成功，放行
   * @param request
   * @param response
   * @param handler
   * @return
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
     if(!(handler instanceof HandlerMethod)){
       //handler 如果不是登陆请求,即可能是static 下的静态资源请求，需要放行
       return true;
     }
     //判断token是否为空
    String token = request.getHeader("Authorization");
    log.info("=================request start===========================");
    String requestURI = request.getRequestURI();
    log.info("request uri:{}",requestURI);
    log.info("request method:{}",request.getMethod());
    log.info("token:{}", token);
    log.info("=================request end===========================");
    if(StringUtils.isBlank(token)){
      Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
      response.setContentType("application/json;charset=utf-8");
      response.getWriter().print(JSON.toJSONString(result));
      return false;
    }
    SysUser sysUser = loginService.checkToken(token);
    if(sysUser==null){
      Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
      response.setContentType("application/json;charset=utf-8");
      response.getWriter().print(JSON.toJSONString(result));
      return false;
    }
    //存入本地线程  ==线程隔离的=>线程安全问题解决方案之一
    UserThreadLocal.set(sysUser);
    //登陆成功后，放行
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    //如果不删除threadLocal中的信息，会有内存泄漏的风险
    UserThreadLocal.delete();
  }
}
