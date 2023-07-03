package com.xiu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiu.blog.pojo.BlogTag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 锈渎 on 2023/6/25 23:49
 */
@Mapper
public interface BlogTagMapper extends BaseMapper<BlogTag> {

    @Delete("DELETE FROM blog_tag WHERE blog_id = #{blog_id}")
    void deleteTagByBlog(@Param("blog_id") Integer blogId);

    @Select("SELECT tag_id FROM blog_tag WHERE blog_id = #{blog_id}")
    List<Integer> listTagIdsByBlog(@Param("blog_id") Integer blogId);
}
