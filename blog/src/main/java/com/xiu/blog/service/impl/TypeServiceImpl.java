package com.xiu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiu.blog.config.NotFoundException;
import com.xiu.blog.mapper.BlogMapper;
import com.xiu.blog.mapper.TypeMapper;
import com.xiu.blog.pojo.Blog;
import com.xiu.blog.pojo.Type;
import com.xiu.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author: 锈渎
 * @date: 2023/6/26 10:52
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Transactional
    @Override
    public int insertType(Type type) {
        return typeMapper.insert(type);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.selectByName(name);
    }

    @Override
    public List<Type> listTypeAll() {
        List<Type> types = typeMapper.selectList(null);
        for(Type type : types){
            QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type_id", type.getId());
            type.setCount(Math.toIntExact(blogMapper.selectCount(queryWrapper)));
        }
        return types;
    }

    @Override
    public Type getType(int id) {
        return typeMapper.selectById(id);
    }

    @Override
    public Page<Type> listType(int pageNum) {
        Page<Type> page = new Page<>(pageNum, 10);
        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return typeMapper.selectPage(page, queryWrapper);
    }

    @Transactional
    @Override
    public int updateType(int id, Type new_type) {
        Type type = typeMapper.selectById(id);
        if(type == null){
            throw new NotFoundException("该类型不存在或已被删除");
        }
        new_type.setId(id);
        return typeMapper.updateById(new_type);
    }

    @Transactional
    @Override
    public int deleteType(int id) {
        return typeMapper.deleteById(id);
    }
}
