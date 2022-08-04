package com.xuge.mszl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuge.mszl.bean.Article;
import com.xuge.mszl.bean.dos.Archives;
import com.xuge.mszl.common.Result;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * author: yjx
 * Date :2022/7/2811:38
 **/
public interface ArticleMapper extends BaseMapper<Article> {
  List<Archives> listArchives();

  IPage<Article> listArticle(Page<Article> page, Long categoryId, Long tagId, String year, String month);
}
