package com.xuge.mszl.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuge.mszl.bean.*;
import com.xuge.mszl.bean.dos.Archives;
import com.xuge.mszl.bean.params.ArticleParam;
import com.xuge.mszl.bean.params.PageParams;
import com.xuge.mszl.bean.vo.ArticleBodyVo;
import com.xuge.mszl.bean.vo.ArticleVo;
import com.xuge.mszl.bean.vo.CategoryVo;
import com.xuge.mszl.bean.vo.TagVo;
import com.xuge.mszl.common.Result;
import com.xuge.mszl.mapper.ArticleBodyMapper;
import com.xuge.mszl.mapper.ArticleMapper;
import com.xuge.mszl.mapper.ArticleTagMapper;
import com.xuge.mszl.mapper.CategoryMapper;
import com.xuge.mszl.service.ArticleService;
import com.xuge.mszl.service.SysUserService;
import com.xuge.mszl.service.TagsService;
import com.xuge.mszl.service.common.ThreadService;
import com.xuge.mszl.utils.UserThreadLocal;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * author: yjx
 * Date :2022/7/2811:32
 **/

@Service
public class ArticleServiceImpl extends ServiceImpl< ArticleMapper,Article> implements ArticleService {

  @Autowired
  private SysUserService sysUserService;
  @Autowired
  private TagsService tagsService;
  @Autowired
  private ArticleBodyMapper articleBodyMapper;
  @Autowired
  private CategoryMapper categoryMapper;
  @Autowired
  private ThreadService threadService;
  @Autowired
  private ArticleTagMapper articleTagMapper;

  public ArticleVo copy(Article article, boolean isAuthor, boolean isBody, boolean isTags,boolean isCat){
    ArticleVo articleVo = new ArticleVo();
    //设置Id
    articleVo.setId(String.valueOf(article.getId()));
    BeanUtils.copyProperties(article, articleVo);
    if (isAuthor) {
      SysUser sysUser = sysUserService.findSysUserById(article.getAuthorId());
      articleVo.setAuthor(sysUser.getNickname());
    }
    articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
    if (isTags){
      List<TagVo> tags = tagsService.findTagsByArticleId(article.getId());
      articleVo.setTags(tags);
    }
    if (isBody){
      ArticleBodyVo articleBody = findArticleBody(article.getBodyId());
      articleVo.setBody(articleBody);
    }
    if (isCat){
      List<CategoryVo> categoryVoList = findCategory(article.getId());
      articleVo.setCategorys(categoryVoList);
    }
    return articleVo;
  }
  //根据文章id查询对应的评论
  private List<CategoryVo> findCategory(Long id) {
    //1.先查询对应文章下的评论id集合
    QueryWrapper<Article> qw = new QueryWrapper<>();
    qw.eq("id",id);
    qw.select("category_id");
    List<Article> list = this.list(qw);
    List<Long> ids=new ArrayList<>();
    //遍历list，取出每一个id
    for (Article article : list) {
      ids.add(article.getCategoryId());
    }
    //2.根据评论id集合，查询评论表，并封装数据
    List<Category> categories = categoryMapper.selectBatchIds(ids);
    List<CategoryVo>categoryVoList=new ArrayList<>();
    for (Category category : categories) {
      CategoryVo categoryVo = new CategoryVo();
      BeanUtils.copyProperties(category, categoryVo);
      categoryVoList.add(categoryVo);
    }
    //返回集合
    return  categoryVoList;
  }


  private ArticleBodyVo findArticleBody(Long id) {
    ArticleBody articleBody = articleBodyMapper.selectById(id);
    ArticleBodyVo articleBodyVo = new ArticleBodyVo();
    articleBodyVo.setContent(articleBody.getContent());
    return articleBodyVo;
  }

  //循环遍历每一个数据将model转成vo
  private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isBody, boolean isTags,boolean isCat) {
    List<ArticleVo> articleVoList = new ArrayList<>();
    for (Article article : records) {
      ArticleVo articleVo = copy(article,isAuthor,isBody,isTags,isCat);
      articleVoList.add(articleVo);
    }
    return articleVoList;
  }

  //循环遍历每一个数据将model转成vo
  private List<ArticleVo> copyList(List<Article> records, boolean isAuthor, boolean isTags) {
    List<ArticleVo> articleVoList = new ArrayList<>();
    for (Article article : records) {
      ArticleVo articleVo = copy(article,isAuthor,isTags);
      articleVoList.add(articleVo);
    }
    return articleVoList;
  }

  private ArticleVo copy(Article article, boolean isAuthor, boolean isTags) {
    ArticleVo articleVo = new ArticleVo();
    //设置Id
    articleVo.setId(String.valueOf(article.getId()));
    BeanUtils.copyProperties(article, articleVo);
    if (isAuthor) {
      SysUser sysUser = sysUserService.findSysUserById(article.getAuthorId());
      articleVo.setAuthor(sysUser.getNickname());
    }
    articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
    if (isTags){
      List<TagVo> tags = tagsService.findTagsByArticleId(article.getId());
      articleVo.setTags(tags);
    }
    return articleVo;
  }

  @Override
  public List<ArticleVo> listArticlesPage(PageParams pageParams) {
    Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
    IPage<Article> articleIPage = baseMapper.listArticle(page,pageParams.getCategoryId(),pageParams.getTagId(),pageParams.getYear(),pageParams.getMonth());
    return copyList(articleIPage.getRecords(),true,true);
  }

  /**
   * 分页查询文章列表
   * @param pageParams  分页条件
   * @return
   */
//  @Override
//  public List<ArticleVo> listArticlesPage(PageParams pageParams) {
//    QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
//    Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
//    Long categoryId = pageParams.getCategoryId();
//    if(pageParams.getCategoryId()!=null){
//      queryWrapper.eq("category_id",categoryId);
//    }
//    List<Long> articleIdList = new ArrayList<>();
//    if (pageParams.getTagId() != null){
//      LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//      articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
//      List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//      for (ArticleTag articleTag : articleTags) {
//        articleIdList.add(articleTag.getArticleId());
//      }
//      if (articleIdList.size() > 0){
//        queryWrapper.in("id",articleIdList);
//      }
//    }
//    //是否置顶排序
//    queryWrapper.orderByDesc("weight","create_date");
//    Page<Article> articlePage = baseMapper.selectPage(page, queryWrapper);
//    //再对数据进行封装成所需的数据
//    List<ArticleVo> articleVoList = copyList(articlePage.getRecords(),true,false,true,false);
//    return articleVoList;
//  }
  //获取最热文章，取前5条文章数据
  //返回 文章 Id 以及文章标题
  @Override
  public Result hotArticle(int i) {
    QueryWrapper<Article> qw = new QueryWrapper<>();
    //按照浏览量倒序排序
    qw.orderByDesc("view_counts");
    qw.select("id","title");
    qw.last("limit " + i);
    List<Article> articles = baseMapper.selectList(qw);
    return Result.success(copyList(articles,false,false,false ,false));
  }
  //最新文章实现
  @Override
  public Result newArticle(int i) {

    QueryWrapper<Article> qw = new QueryWrapper<>();
    //按照浏览量倒序排序
    qw.orderByDesc("create_date");
    qw.select("id","title");
    qw.last("limit " + i);
    List<Article> articles = baseMapper.selectList(qw);
    return Result.success(copyList(articles,false,false,false,false));

  }

  /**
   *
   * @param i
   * @return
   */
  @Override
  public Result listArchives() {
    List<Archives> archivesList = baseMapper.listArchives();
    return Result.success(archivesList);
  }

  /**
   * 1.根据id查询文章信息
   * 2.根据bodyid 和 categoryId 关联查询
   * @param id  文章id
   * @return
   */
  @Override
  public ArticleVo findArticleById(Long id) {
    //1.查询文章信息
    Article article = this.getById(id);
    //查看文章之后，阅读数加1
    //更新时加写锁，阻塞其他操作，性能会比较低
    //更新增加了此次接口的耗时，如果更新出问题，不能影响阅读操作

    //使用线程池解决
    //可以把更新操作放入线程池，和主线程不影响
    threadService.updateViewCount(baseMapper,article);
    //2.封装vo对象并返回
    return copy(article,true,true,true,true);
  }
  //发布文章实现
  @Override
  @Transactional
  public Result publish(ArticleParam articleParam) {
    //封装文章对象
    Article article = new Article();
    //获取当前用户ID
    SysUser sysUser

            = UserThreadLocal.get();
    article.setAuthorId(sysUser.getId());
    //设置创建时间

    article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
    article.setCreateDate(System.currentTimeMillis());
    article.setCommentCounts(0);
    article.setSummary(articleParam.getSummary());
    article.setTitle(articleParam.getTitle());
    article.setViewCounts(0);
    article.setWeight(Article.Article_Common);
    article.setBodyId(-1L);
    //对文章表执行插入
    baseMapper.insert(article);



    //对文章标签表执行插入
    List<TagVo> tags = articleParam.getTags();
    if(tags!=null){
      for (TagVo tag : tags) {
        ArticleTag tag1 = new ArticleTag();
        tag1.setArticleId(article.getId());
        tag1.setTagId(Long.parseLong(tag.getId()));
        articleTagMapper.insert(tag1);
      }
    }

    ArticleBody articleBody = new ArticleBody();
    articleBody.setContent(articleParam.getBody().getContent());
    articleBody.setContentHtml(articleParam.getBody().getContentHtml());
    articleBody.setArticleId(article.getId());
    articleBodyMapper.insert(articleBody);
    //再重新更新文章表，加入文章内容id
    article.setBodyId(articleBody.getId());
    baseMapper.updateById(article);
    //构建响应数据
    ArticleVo articleVo = new ArticleVo();
    articleVo.setId(String.valueOf(article.getId()));
    return Result.success(articleVo);
  }


}
