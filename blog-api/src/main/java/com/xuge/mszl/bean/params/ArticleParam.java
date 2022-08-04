package com.xuge.mszl.bean.params;

import com.xuge.mszl.bean.vo.CategoryVo;
import com.xuge.mszl.bean.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * author: yjx
 * Date :2022/8/217:49
 **/
@Data
public class ArticleParam {

  private Long id;

  private ArticleBodyParam body;

  private CategoryVo category;

  private String summary;

  private List<TagVo> tags;

  private String title;
}
