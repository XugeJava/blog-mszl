package com.xuge.mszl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuge.mszl.bean.Tag;

import java.util.List;

/**
 * author: yjx
 * Date :2022/7/2813:38
 **/
public interface TagMapper extends BaseMapper<Tag> {
  List<Tag> queryTagsById(Long id);

  List<Long> queryHotTags(int i);
}
