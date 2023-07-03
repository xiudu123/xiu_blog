package com.xiu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiu.blog.mapper.UserMapper;
import com.xiu.blog.pojo.User;
import com.xiu.blog.service.UserService;
import com.xiu.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 锈渎
 * @date: 2023/6/26 1:40
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 实现跟 User 表相关的操作
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
        return userMapper.selectByUsernameAndPassword(username, MD5Utils.code(password));
    }

}
