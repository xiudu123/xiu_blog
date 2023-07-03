package com.xiu.blog.service;

import com.xiu.blog.pojo.User;

/**
 * Created by 锈渎 on 2023/6/26 1:39
 */
public interface UserService {
    User checkUser(String username, String password);
}
