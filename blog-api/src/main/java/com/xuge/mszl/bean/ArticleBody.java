package com.xuge.mszl.bean;

import lombok.Data;

/**
 * author: yjx
 * Date :2022/8/115:44
 **/
@Data
public class ArticleBody {

  private Long id;
  private String content;
  private String contentHtml;
  private Long articleId;
}