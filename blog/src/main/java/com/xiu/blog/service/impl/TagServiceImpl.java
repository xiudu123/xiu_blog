package com.xiu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiu.blog.config.NotFoundException;
import com.xiu.blog.mapper.TagMapper;
import com.xiu.blog.pojo.Tag;
import com.xiu.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 锈渎
 * @date: 2023/6/28 12:52
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public int insertTag(Tag tag) {
        return tagMapper.insert(tag);
    }

    @Override
    public Tag getTag(int id) {
        return tagMapper.selectById(id);
    }

    @Override
    public List<Tag> listTagAll() {
        return tagMapper.selectList(null);
    }

    @Override
    public Page<Tag> listTag(int pageNum) {
        Page<Tag> page = new Page<>(pageNum, 10);
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return tagMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.selectByName(name);
    }

    @Override
    public int updateTag(int id, Tag new_tag) {
        Tag tag = tagMapper.selectById(id);
        if(tag == null){
            throw new NotFoundException("该类型不存在或已被删除");
        }
        new_tag.setId(id);
        return tagMapper.updateById(new_tag);
    }

    @Override
    public int deleteTag(int id) {
        return tagMapper.deleteById(id);
    }
}
