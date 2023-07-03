package com.xiu.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author: 锈渎
 * @date: 2023/6/25 23:40
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 关联数据库 blog 表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private String firstPicture;
    private Integer views;
    private Boolean top;
    private Boolean published;
    private Boolean comment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    private String overview;

    private Integer userId;
    private Integer typeId;

    private transient String typeName; // 不与数据库关联;
    private transient List<Tag> tagList;
    private transient List<Integer> tagIds;
    private transient String userName;
    private transient Integer year;
    private transient Integer month;
    private transient Integer day;
}
