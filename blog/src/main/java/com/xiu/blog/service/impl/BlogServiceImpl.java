package com.xiu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiu.blog.config.NotFoundException;
import com.xiu.blog.mapper.*;
import com.xiu.blog.pojo.Blog;
import com.xiu.blog.pojo.BlogTag;
import com.xiu.blog.pojo.Tag;
import com.xiu.blog.pojo.Type;
import com.xiu.blog.service.BlogService;
import com.xiu.blog.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * @author: 锈渎
 * @date: 2023/6/28 13:55
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Override
    public Page<Blog> listBlogByTypeId(Integer pageNum, Integer typeId) {
        Page<Blog> page = new Page<>(pageNum, 10);

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top");

        QueryWrapper<Type> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", typeId);
        queryWrapper.orderByDesc( "update_time");
        if(typeMapper.selectCount(queryWrapper1) > 0) queryWrapper.eq("type_id", typeId);

        Page<Blog> blogPage = blogMapper.selectPage(page, queryWrapper);
        for(Blog blog : blogPage.getRecords()){
            blog.setTypeName(typeMapper.selectById(blog.getTypeId()).getName());
            blog.setUserName(userMapper.selectById(blog.getUserId()).getNickname());
        }
        return blogPage;
    }

    @Override
    public Integer blogCount() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1);
        return Math.toIntExact(blogMapper.selectCount(queryWrapper));
    }

    @Override
    public List<Blog> listTop(Integer size) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "update_time").last("LIMIT " + size.toString());
        return blogMapper.selectList(queryWrapper);
    }

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Blog> listBlogAll() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "update_time");
        List<Blog> blogs =  blogMapper.selectList(queryWrapper);
        for(Blog blog : blogs){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            LocalDateTime dateTime = LocalDateTime.parse(blog.getUpdateTime().toString(), formatter);
            blog.setYear(dateTime.getYear());
            blog.setMonth(dateTime.getMonthValue());
            blog.setDay(dateTime.getDayOfMonth());
        }

        return blogs;
    }

    @Override
    public Blog getAndConvert(int id) {
        Blog blog = getBlog(id);
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blog.setViews(blog.getViews() + 1);
        blogMapper.updateViewById(id);
        return blog;
    }

    @Override
    public int insertBlog(Blog blog) {
        return blogMapper.insert(blog);
    }

    List<Tag> blogTags(int id){
        List<Integer> tagIds = blogTagMapper.listTagIdsByBlog(id);
        if(tagIds == null || tagIds.size() == 0) return null;

        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.or().in("id", tagIds);
        return tagMapper.selectList(queryWrapper);
    }

    @Override
    public Blog getBlog(int id) {
        Blog blog = blogMapper.selectById(id);
        if(blog == null){
            throw new NotFoundException("该博客不存在");
        }
        blog.setTypeName(typeMapper.selectById(blog.getTypeId()).getName());
        blog.setUserName(userMapper.selectById(blog.getUserId()).getNickname());
        blog.setTagList(blogTags(id));
        return blog;
    }

    @Override
    public Page<Blog> listBlogSearch(Integer pageNum, String query) {
        Page<Blog> page = new Page<>(pageNum, 10);

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "create_time");
        queryWrapper.like("title", query).or().like("content", query);
        Page<Blog> blogPage = blogMapper.selectPage(page, queryWrapper);
        for(Blog blog : blogPage.getRecords()){
            blog.setTypeName(typeMapper.selectById(blog.getTypeId()).getName());
            blog.setUserName(userMapper.selectById(blog.getUserId()).getNickname());
        }
        return blogPage;
    }

    @Override
    public Page<Blog> listBlogIndex(Integer pageNum) {
        Page<Blog> page = new Page<>(pageNum, 10);

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("published", 1).orderByDesc("top", "create_time");

        Page<Blog> blogPage = blogMapper.selectPage(page, queryWrapper);
        for(Blog blog : blogPage.getRecords()){
            blog.setTypeName(typeMapper.selectById(blog.getTypeId()).getName());
            blog.setUserName(userMapper.selectById(blog.getUserId()).getNickname());
        }
        return blogPage;
    }

    @Override
    public Page<Blog> listBlog(Integer pageNum, Blog blogRules) {
        Page<Blog> page = new Page<>(pageNum, 10);

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();

        // 查询条件;
        if(!isEmpty(blogRules.getTitle())){
            queryWrapper.like("title", blogRules.getTitle());
        }
        if(!isEmpty(blogRules.getTypeId()) && blogRules.getTypeId() > 0){
            queryWrapper.eq("type_id", blogRules.getTypeId());
        }
        if(!isEmpty(blogRules.getTop()) && blogRules.getTop()){
            queryWrapper.eq("top", blogRules.getTop());
        }
        if(!isEmpty(blogRules.getPublished()) && !blogRules.getPublished()){
            queryWrapper.eq("published", blogRules.getPublished());
        }
        queryWrapper.orderByDesc("update_time");


        Page<Blog> blogPage = blogMapper.selectPage(page, queryWrapper);
        for(Blog blog : blogPage.getRecords()){
            blog.setTypeName(typeMapper.selectById(blog.getTypeId()).getName());
            blog.setUserName(userMapper.selectById(blog.getUserId()).getNickname());
        }
        return blogPage;
    }

    @Override
    public int updateBlog(int id, Blog new_blog) {
        Blog blog = blogMapper.selectById(id);
        if(blog == null){
            throw new NotFoundException("该博客不存在或已被删除");
        }
        new_blog.setId(id);
        return blogMapper.updateById(new_blog);
    }

    @Override
    public int deleteBlog(int id) {
        return blogMapper.deleteById(id);
    }

    Boolean isEmpty(String object){
        return "".equals(object) || object == null;
    }

    Boolean isEmpty(Boolean object){
        return object == null;
    }

    Boolean isEmpty(Integer object){
        return object == null;
    }
}
