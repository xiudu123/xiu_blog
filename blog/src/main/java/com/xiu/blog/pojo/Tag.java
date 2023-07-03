package com.xiu.blog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: 锈渎
 * @date: 2023/6/25 23:36
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 关联数据库 tag 表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;
}
