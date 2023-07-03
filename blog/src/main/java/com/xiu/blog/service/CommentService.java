package com.xiu.blog.service;

import com.xiu.blog.pojo.Comment;

import java.util.List;

/**
 * Created by 锈渎 on 2023/6/30 22:54
 */
public interface CommentService {

    List<Comment> listCommentByBLogId(int blogId);

    int insertComment(Comment comment);
}
