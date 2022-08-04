package com.xuge.mszl.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuge.mszl.bean.Category;
import com.xuge.mszl.bean.vo.CategoryVo;
import com.xuge.mszl.common.Result;
import com.xuge.mszl.mapper.CategoryMapper;
import com.xuge.mszl.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * author: yjx
 * Date :2022/8/217:36
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
  //查询所有分类
  @Override
  public Result findAll() {
    QueryWrapper<Category> qw = new QueryWrapper<>();
    qw.select("id","avatar","category_name");
    List<Category> categories = baseMapper.selectList(qw);
    return Result.success(copyList(categories));
  }

  @Override
  public Result findAllDetail() {
    List<Category> categories = baseMapper.selectList(new QueryWrapper<Category>());
    return Result.success(copyList(categories));
  }

  @Override
  public Result categoriesDetailById(Long id) {
    return Result.success(copy(baseMapper.selectById(id)));
  }

  private List<CategoryVo> copyList(List<Category> categories) {
    List<CategoryVo> list=new ArrayList<>();
    for (Category category : categories) {
      list.add(copy(category));
    }
    return  list;
  }
  //将普通类型分封装成vo类型数据

  private CategoryVo copy(Category category) {
    CategoryVo categoryVo = new CategoryVo();
    //设置id类型
    categoryVo.setId(String.valueOf(category.getId()));
    categoryVo.setAvatar(category.getAvatar());
    categoryVo.setCategoryName(category.getCategoryName());
    return categoryVo;
  }
}
