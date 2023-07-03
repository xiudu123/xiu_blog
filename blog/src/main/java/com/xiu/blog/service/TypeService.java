package com.xiu.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiu.blog.pojo.Type;

import java.util.List;

/**
 * Created by 锈渎 on 2023/6/26 10:48
 */
public interface TypeService {

    int insertType(Type type);

    Type getType(int id);
    Page<Type> listType(int pageNum);
    Type getTypeByName(String name);
    List<Type> listTypeAll();

    int updateType(int id, Type type);
    int deleteType(int id);
}
