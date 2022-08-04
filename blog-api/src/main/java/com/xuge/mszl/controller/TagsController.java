package com.xuge.mszl.controller;

import com.xuge.mszl.common.Result;
import com.xuge.mszl.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author: yjx
 * Date :2022/7/2814:16
 **/
@RequestMapping("tags")
@RestController
@CrossOrigin
public class TagsController {
  @Autowired
  private TagsService tagsService;
  @GetMapping("hot")
  public Result hot(){
    return tagsService.hots(6);
  }
  @GetMapping
  public Result getAll(){
    return tagsService.findAll();
  }

  @GetMapping("detail")
  public Result findAllDetail(){
    return tagsService.findAllDetail();
  }


  @GetMapping("detail/{id}")
  public Result findDetailById(@PathVariable("id") Long id){
    return tagsService.findDetailById(id);
  }


}
