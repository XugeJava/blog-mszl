package com.xuge.mszl.utils;

import com.xuge.mszl.bean.SysUser;

/**
 * author: yjx
 * Date :2022/8/115:30
 **/
public class UserThreadLocal {
  private UserThreadLocal(){}
  private  static final  ThreadLocal<SysUser> LOCAL=new ThreadLocal();
  public  static void  set(SysUser sysUser){
    LOCAL.set(sysUser);
  }
  public  static SysUser get(){
    return LOCAL.get();
  }
  public static  void delete(){
    LOCAL.remove();
  }

}
