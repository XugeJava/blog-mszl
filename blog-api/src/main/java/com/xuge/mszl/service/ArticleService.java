package com.xuge.mszl.service;

import com.xuge.mszl.bean.params.ArticleParam;
import com.xuge.mszl.bean.params.PageParams;
import com.xuge.mszl.bean.vo.ArticleVo;
import com.xuge.mszl.common.Result;

import java.util.List;

/**
 * author: yjx
 * Date :2022/7/2811:32
 **/
public interface ArticleService {
  List<ArticleVo> listArticlesPage(PageParams pageParams);

  Result hotArticle(int i);

  Result newArticle(int i);

  Result listArchives();
  //查看文章详情
  ArticleVo findArticleById(Long id);
  //发布文章
  Result publish(ArticleParam articleParam);
}
