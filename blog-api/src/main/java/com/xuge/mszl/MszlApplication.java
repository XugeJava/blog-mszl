package com.xuge.mszl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * author: yjx
 * Date :2022/7/2811:19
 **/
@Slf4j
@SpringBootApplication
public class MszlApplication {
  public static void main(String[] args) {
    SpringApplication.run(MszlApplication.class,args);
    log.info("==============码神之路启动成功==============");
  }
}
