package com.xiu.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 锈渎
 * @date: 2023/6/25 23:46
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogTag {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer blogId;

    private Integer tagId;
}
