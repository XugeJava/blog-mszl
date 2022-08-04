package com.xuge.mszl.controller.common;

import com.xuge.mszl.common.Result;
import com.xuge.mszl.utils.QiniuUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * author: yjx
 * Date :2022/8/310:00
 **/
@RestController
@RequestMapping("upload")
@CrossOrigin
public class UploadController {
  @Autowired
  private QiniuUtils qiniuUtils;
  @PostMapping
  public Result upload(@RequestParam("image") MultipartFile file){
    //http://rg0oeit12.hn-bkt.clouddn.com/
    String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
    boolean upload = qiniuUtils.upload(file, fileName);
    if (upload){
      return Result.success(QiniuUtils.url + fileName);
    }
    return Result.fail(20001,"上传失败");
  }
}
