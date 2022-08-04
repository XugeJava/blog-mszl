package com.xuge.mszl.service.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xuge.mszl.bean.Article;
import com.xuge.mszl.mapper.ArticleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * author: yjx
 * Date :2022/8/116:57
 **/
@Component
@Slf4j
public class ThreadService {
  @Async("taskExecutor")
  public void updateViewCount(ArticleMapper baseMapper, Article article) {
    int viewCounts = article.getViewCounts();
    Article article1 = new Article();
    article1.setViewCounts(viewCounts+1);
    QueryWrapper<Article> qw = new QueryWrapper<>();
    qw.eq("id",article.getId());
    qw.eq("view_counts",article.getViewCounts());
    //执行更新
    baseMapper.update(article1,qw);

    //睡眠3秒，证明不影响主线程
    try {
      TimeUnit.SECONDS.sleep(3);
      log.info("更新阅读数，阅读数已加1...");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
