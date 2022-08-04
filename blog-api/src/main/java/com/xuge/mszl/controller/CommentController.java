package com.xuge.mszl.controller;

import com.xuge.mszl.bean.params.CommentParam;
import com.xuge.mszl.common.Result;
import com.xuge.mszl.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author: yjx
 * Date :2022/8/117:22
 **/
@RestController
@CrossOrigin
@RequestMapping("comments")
public class CommentController {
  @Autowired
  private CommentService commentService;
  @GetMapping("article/{id}")
  public Result commentList(@PathVariable Long id){

    return commentService.commentList(id);
  }
  @PostMapping("create/change")
  public Result  comment(@RequestBody CommentParam commentParam){
    return commentService.comment(commentParam);
  }
}
