package com.xiu.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiu.blog.pojo.Blog;

import java.util.List;

/**
 * Created by 锈渎 on 2023/6/28 13:50
 */
public interface BlogService {
    int insertBlog(Blog blog);

    Blog getBlog(int id);
    Page<Blog> listBlog(Integer pageNum, Blog blogRules);
    Page<Blog> listBlogIndex(Integer pageNum);
    Page<Blog> listBlogSearch(Integer pageNum, String query);
    Page<Blog> listBlogByTypeId(Integer pageNum, Integer typeId);
    List<Blog> listBlogAll();
    List<Blog> listTop(Integer size);

    Blog getAndConvert(int id);

    Integer blogCount();

    int updateBlog(int id, Blog blog);

    int deleteBlog(int id);
}
