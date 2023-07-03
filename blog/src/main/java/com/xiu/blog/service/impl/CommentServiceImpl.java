package com.xiu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiu.blog.mapper.CommentMapper;
import com.xiu.blog.pojo.Comment;
import com.xiu.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: 锈渎
 * @date: 2023/6/30 22:56
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> listCommentByBLogId(int blogId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("create_time");
        queryWrapper.eq("blog_id", blogId);
        return commentMapper.selectList(queryWrapper);
    }

    @Override
    public int insertComment(Comment comment) {
        return commentMapper.insert(comment);
    }


}
