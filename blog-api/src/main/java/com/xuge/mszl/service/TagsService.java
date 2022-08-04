package com.xuge.mszl.service;

import com.xuge.mszl.bean.vo.TagVo;
import com.xuge.mszl.common.Result;

import java.util.List;

/**
 * author: yjx
 * Date :2022/7/2813:36
 **/
public interface TagsService {

  List<TagVo> findTagsByArticleId(Long id);

  Result hots(int i);

  Result findAll();

  Result findAllDetail();

  Result findDetailById(Long id);
}
