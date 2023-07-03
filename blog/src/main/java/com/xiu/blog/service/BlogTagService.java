package com.xiu.blog.service;

import com.xiu.blog.pojo.BlogTag;
import com.xiu.blog.pojo.Tag;

import java.util.List;

/**
 * Created by 锈渎 on 2023/6/30 11:02
 */
public interface BlogTagService {
    void insertBlogTag(Integer blogId, List<Integer> ids);
    void deleteBlogTag(Integer blogId);
    List<Integer> listTagIds(Integer blogId);
}
