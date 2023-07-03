package com.xiu.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiu.blog.pojo.Tag;

import java.util.List;

/**
 * Created by 锈渎 on 2023/6/28 12:51
 */
public interface TagService {
    int insertTag(Tag tag);

    Tag getTag(int id);
    Page<Tag> listTag(int pageNum);
    Tag getTagByName(String name);
    List<Tag> listTagAll();
    int updateTag(int id, Tag tag);
    int deleteTag(int id);
}
