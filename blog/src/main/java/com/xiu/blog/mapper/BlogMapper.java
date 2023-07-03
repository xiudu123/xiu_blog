package com.xiu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiu.blog.pojo.Blog;
import com.xiu.blog.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by 锈渎 on 2023/6/25 23:49
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    @Update("UPDATE blog SET views = views+1 WHERE id= #{id}")
    void updateViewById(int id);
}
