package com.xiu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiu.blog.mapper.BlogTagMapper;
import com.xiu.blog.pojo.BlogTag;
import com.xiu.blog.pojo.Tag;
import com.xiu.blog.service.BlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 锈渎
 * @date: 2023/6/30 11:03
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Service
public class BlogTagServiceImpl implements BlogTagService {
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Override
    public void insertBlogTag(Integer blogId, List<Integer> ids) {
        for(int tagId : ids){
            blogTagMapper.insert(new BlogTag(null, blogId, tagId));
        }
    }

    @Override
    public void deleteBlogTag(Integer blogId) {
        blogTagMapper.deleteTagByBlog(blogId);
    }

    @Override
    public List<Integer> listTagIds(Integer blogId) {
        return blogTagMapper.listTagIdsByBlog(blogId);
    }
}
