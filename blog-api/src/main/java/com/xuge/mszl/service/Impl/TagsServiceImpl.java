package com.xuge.mszl.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuge.mszl.bean.Tag;
import com.xuge.mszl.bean.vo.TagVo;
import com.xuge.mszl.common.Result;
import com.xuge.mszl.mapper.TagMapper;
import com.xuge.mszl.service.TagsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * author: yjx
 * Date :2022/7/2813:37
 **/
@Service
public class TagsServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagsService {
 //根据文章id找出对应的评论
  @Override
  public List<TagVo> findTagsByArticleId(Long id) {
    List<Tag> tagsList=baseMapper.queryTagsById(id);
    return copyList(tagsList);
  }
  //返回最热门的标签
  //标签所拥有的文章数量最多的即为最热，取前六
  @Override
  public Result hots(int i) {
    List<Long> ids=baseMapper.queryHotTags(i);
    //遍历集合,取出每个标签
    //创建一个标签集合，接收数据
    List<Tag>  tagList=new ArrayList<>();
    if (CollectionUtils.isEmpty(ids)){
      return Result.success(Collections.emptyList());
    }

    for (Long id : ids) {
      //根据标签id获取获取标签名称
      Tag tag = baseMapper.selectById(id);
      tagList.add(tag);
    }
    return Result.success(tagList);
  }
  //查询所有标签
  @Override
  public Result findAll() {
    QueryWrapper<Tag> qw = new QueryWrapper<>();
    qw.select("tag_name","id");
    return Result.success(copyList(baseMapper.selectList(qw)));
  }

  @Override
  public Result findAllDetail() {
    return Result.success(copyList(baseMapper.selectList(new QueryWrapper<Tag>())));
  }

  @Override
  public Result findDetailById(Long id) {
    return Result.success(copy(baseMapper.selectById(id)));
  }

  public TagVo copy(Tag tag){
    TagVo tagVo = new TagVo();
    tagVo.setId(String.valueOf(tag.getId()));
    BeanUtils.copyProperties(tag,tagVo);
    return tagVo;
  }
  public List<TagVo> copyList(List<Tag> tagList){
    List<TagVo> tagVoList = new ArrayList<>();
    for (Tag tag : tagList) {
      tagVoList.add(copy(tag));
    }
    return tagVoList;
  }

}
