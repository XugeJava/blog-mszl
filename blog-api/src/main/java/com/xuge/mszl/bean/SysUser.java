package com.xuge.mszl.bean;

import lombok.Data;

/**
 * author: yjx
 * Date :2022/7/2811:29
 **/

@Data
public class SysUser {

  private Long id;

  private String account;

  private Integer admin;

  private String avatar;

  private Long createDate;

  private Integer deleted;

  private String email;

  private Long lastLogin;

  private String mobilePhoneNumber;

  private String nickname;

  private String password;

  private String salt;

  private String status;
}
