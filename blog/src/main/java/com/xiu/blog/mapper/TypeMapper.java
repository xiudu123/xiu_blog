package com.xiu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiu.blog.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by 锈渎 on 2023/6/25 23:52
 */
@Mapper
public interface TypeMapper extends BaseMapper<Type> {

    @Select("SELECT * FROM type WHERE name = #{name}")
    Type selectByName(@Param("name") String name);
}
