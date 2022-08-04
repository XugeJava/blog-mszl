package com.xuge.mszl.bean;

import lombok.Data;

/**
 * author: yjx
 * Date :2022/8/117:21
 **/
@Data
public class Comment {

  private Long id;

  private String content;

  private Long createDate;

  private Long articleId;

  private Long authorId;

  private Long parentId;

  private Long toUid;

  private Integer level;
}
