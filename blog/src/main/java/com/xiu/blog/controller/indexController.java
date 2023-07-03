package com.xiu.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiu.blog.pojo.Blog;
import com.xiu.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: 锈渎
 * @date: 2023/6/30 14:54
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Controller
@RequestMapping("/")
public class indexController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("")
    public String Index(){
        return "redirect:/index";
    }

    @RequestMapping("index")
    public String index(@RequestParam(defaultValue = "1") Integer pageNum, Model model){
        Page<Blog> blogPage =  blogService.listBlogIndex(pageNum);
        if(blogPage.getCurrent() > blogPage.getPages() || 1 > blogPage.getCurrent()){
            int pages = Math.toIntExact(blogPage.getPages());
            if(pages < 1) pages = 1;
            blogPage = blogService.listBlogIndex(pages);
        }
        model.addAttribute("blogs", blogPage.getRecords());
        model.addAttribute("pageInfo", blogPage);
        model.addAttribute("prePage", (blogPage.getCurrent() - (blogPage.hasPrevious() ? 1 : 0)));
        model.addAttribute("nextPage", (blogPage.getCurrent() + (blogPage.hasNext() ? 1 : 0)));
        return "index";
    }

    // 搜索
    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam String query, Model model){
        Page<Blog> blogPage =  blogService.listBlogSearch(pageNum, query);
        if(blogPage.getCurrent() > blogPage.getPages() || 1 > blogPage.getCurrent()){
            int pages = Math.toIntExact(blogPage.getPages());
            if(pages < 1) pages = 1;
            blogPage = blogService.listBlogSearch(pages, query);
        }
        model.addAttribute("blogs", blogPage.getRecords());
        model.addAttribute("pageInfo", blogPage);
        model.addAttribute("prePage", (blogPage.getCurrent() - (blogPage.hasPrevious() ? 1 : 0)));
        model.addAttribute("nextPage", (blogPage.getCurrent() + (blogPage.hasNext() ? 1 : 0)));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable int id, Model model){
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newBlog")
    public String footerNewBlogs(Model model){
        model.addAttribute("newBlogs", blogService.listTop(3));
        return "_fragments :: newBlogList";
    }

    @GetMapping("/footer/message")
    public String footerBlogCount(Model model){
        model.addAttribute("blogCount", blogService.blogCount());
        return "_fragments :: message";
    }

}
