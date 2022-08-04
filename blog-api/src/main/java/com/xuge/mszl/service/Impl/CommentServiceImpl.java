package com.xuge.mszl.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuge.mszl.bean.Comment;
import com.xuge.mszl.bean.SysUser;
import com.xuge.mszl.bean.params.CommentParam;
import com.xuge.mszl.bean.vo.CommentVo;
import com.xuge.mszl.bean.vo.UserVo;
import com.xuge.mszl.common.Result;
import com.xuge.mszl.mapper.CommentMapper;
import com.xuge.mszl.service.CommentService;
import com.xuge.mszl.service.SysUserService;
import com.xuge.mszl.utils.UserThreadLocal;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * author: yjx
 * Date :2022/8/212:01
 **/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
  @Autowired
  private SysUserService sysUserService;
  //根据文章Id查询对应的评论
  @Override
  public Result commentList(Long id) {
    QueryWrapper<Comment> qw = new QueryWrapper<>();
    qw.orderByDesc("create_date");
    qw.eq("article_id",id);
    qw.eq("level",1l);
    List<Comment> comments = baseMapper.selectList(qw);
    return Result.success(copyList(comments));
  }
  //评论功能实现
  @Override
  public Result comment(CommentParam commentParam) {
    //获取用户信息
    SysUser sysUser= UserThreadLocal.get();
    //封账评论对象
    Comment comment = new Comment();
    comment.setArticleId(commentParam.getArticleId());
    comment.setAuthorId(sysUser.getId());
    comment.setContent(commentParam.getContent());
    comment.setCreateDate(System.currentTimeMillis());
    Long parent = commentParam.getParent();
    if(parent==null||parent==0){
      comment.setLevel(1);
    }else{
      comment.setLevel(2);
    }
    comment.setParentId(parent==null?0:parent);
    Long toUserId = commentParam.getToUserId();
    comment.setToUid(toUserId == null ? 0 : toUserId);
    baseMapper.insert(comment);
    return Result.success(null);
  }

  private  List<CommentVo> copyList(List<Comment> comments) {
    List<CommentVo> commentVos=new ArrayList<>();
    for (Comment comment : comments) {
      commentVos.add(copy(comment));
    }
    return  commentVos;
  }
  //具体的将每一个实体封装成vo对象

  /**
   * 1.封装作者信息
   * 2.当level=1时，查出其对应的子评论列表
   * @param comment
   * @return
   */
  private CommentVo copy(Comment comment) {
    CommentVo commentVo = new CommentVo();
    //设置id
    commentVo.setId(String.valueOf(comment.getId()));
    BeanUtils.copyProperties(comment, commentVo);
    //设置日期
    commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
    //根据作者Id,查询信息
    Long authorId = comment.getAuthorId();
    UserVo userVo = this.sysUserService.findUserVoById(authorId);
    commentVo.setAuthor(userVo);
    
    //评论的评论
    List<CommentVo> commentVoList = findCommentsByParentId(comment.getId());
    commentVo.setChildrens(commentVoList);
    //当评论层级大于1
    if (comment.getLevel() > 1) {
      Long toUid = comment.getToUid();
      UserVo toUserVo = sysUserService.findUserVoById(toUid);
      commentVo.setToUser(toUserVo);
    }
    return commentVo;
  }

  //找出
  private List<CommentVo> findCommentsByParentId(Long id) {
    QueryWrapper<Comment> qw = new QueryWrapper<>();
    qw.eq("parent_id",id);
    qw.eq("level",2l);
    //当前一级评论下对应的二级评论
    List<Comment> list = this.list(qw);
    return copyList(list);
  }
}
