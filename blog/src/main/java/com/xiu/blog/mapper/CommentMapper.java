package com.xiu.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiu.blog.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by 锈渎 on 2023/6/25 23:50
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
