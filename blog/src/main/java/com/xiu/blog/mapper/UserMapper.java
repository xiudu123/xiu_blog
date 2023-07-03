package com.xiu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiu.blog.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by 锈渎 on 2023/6/25 23:52
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 自定义根据用户名和密码查询数据库;
    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    User selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}
