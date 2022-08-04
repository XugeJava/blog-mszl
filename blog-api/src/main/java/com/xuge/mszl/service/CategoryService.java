package com.xuge.mszl.service;

import com.xuge.mszl.common.Result;

/**
 * author: yjx
 * Date :2022/8/217:35
 **/

public interface CategoryService {
  Result findAll();

  Result findAllDetail();

  Result categoriesDetailById(Long id);
}
