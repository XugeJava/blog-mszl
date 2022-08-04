package com.xuge.mszl.bean.params;

import lombok.Data;

/**
 * author: yjx
 * Date :2022/8/214:10
 **/
@Data
public class CommentParam {

  private Long articleId;

  private String content;

  private Long parent;

  private Long toUserId;
}
