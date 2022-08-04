package com.xuge.mszl.service;

import com.xuge.mszl.bean.params.CommentParam;
import com.xuge.mszl.common.Result;

/**
 * author: yjx
 * Date :2022/8/212:01
 **/
public interface CommentService {
  Result commentList(Long id);

  Result comment(CommentParam commentParam);
}
