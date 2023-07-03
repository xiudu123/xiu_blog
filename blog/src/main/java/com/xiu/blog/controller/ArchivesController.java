package com.xiu.blog.controller;

import com.xiu.blog.pojo.Blog;
import com.xiu.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author: 锈渎
 * @date: 2023/7/2 1:38
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Controller
public class ArchivesController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        List<Blog> blogs = blogService.listBlogAll();

        //降序排序
        Map<Integer, List<Blog>> map = new TreeMap<>(Comparator.reverseOrder());
        for(Blog blog : blogs){
            if(!map.containsKey(blog.getYear())) map.put(blog.getYear(), new ArrayList<>());
            map.get(blog.getYear()).add(blog);
        }

        model.addAttribute("blogs", map);
        model.addAttribute("blogTotal", blogs.size());

        return "archives";
    }
}
