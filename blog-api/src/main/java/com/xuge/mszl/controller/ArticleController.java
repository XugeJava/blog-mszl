package com.xuge.mszl.controller;

import com.xuge.mszl.bean.params.ArticleParam;
import com.xuge.mszl.bean.params.PageParams;
import com.xuge.mszl.bean.vo.ArticleVo;
import com.xuge.mszl.common.Result;
import com.xuge.mszl.common.aop.LogAnnotation;
import com.xuge.mszl.common.cache.Cache;
import com.xuge.mszl.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: yjx
 * Date :2022/7/2811:30
 **/
@RequestMapping("articles")
@RestController
@CrossOrigin
public class ArticleController {
  @Autowired
  private ArticleService articleService;

  @PostMapping
  @LogAnnotation
  @Cache(expire = 5 * 60 * 1000,name = "page_article")
  public Result pageArticle(@RequestBody PageParams pageParams) {
    List<ArticleVo> articleVos = articleService.listArticlesPage(pageParams);
    return Result.success(articleVos);
  }

  @PostMapping("hot")
  @Cache(expire = 5 * 60 * 1000,name = "hot_article")
  public Result hotArticles() {
    return articleService.hotArticle(5);
  }

  @PostMapping("new")
  @Cache(expire = 5 * 60 * 1000,name = "new_article")
  public Result newArticles() {
    return articleService.newArticle(5);
  }

  @PostMapping("listArchives")
  public Result listArchives() {
    Result result = articleService.listArchives();
    if(result==null){
      return Result.fail(201,"暂无最新归档..");
    }
    return articleService.listArchives();
  }

  @PostMapping("view/{id}")
  public Result  findArticleById(@PathVariable Long id){
    ArticleVo articleVo = articleService.findArticleById(id);

    return Result.success(articleVo);
  }
  @PostMapping("publish")
  public Result publish(@RequestBody ArticleParam articleParam){

    return articleService.publish(articleParam);
  }



}
