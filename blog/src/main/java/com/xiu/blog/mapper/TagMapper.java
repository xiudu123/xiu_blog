package com.xiu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiu.blog.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by 锈渎 on 2023/6/25 23:51
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    @Select("SELECT * FROM tag WHERE name = #{name}")
    Tag selectByName(@Param("name") String name);
}
