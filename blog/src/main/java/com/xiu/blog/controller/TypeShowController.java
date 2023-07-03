package com.xiu.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiu.blog.pojo.Blog;
import com.xiu.blog.pojo.Type;
import com.xiu.blog.service.BlogService;
import com.xiu.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: 锈渎
 * @date: 2023/7/2 1:11
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/type/{id}")
    public String types(@RequestParam(defaultValue = "1") Integer pageNum,
                        Model model,
                        @PathVariable int id){
        List<Type> types = typeService.listTypeAll();
        model.addAttribute("types", types);

        Page<Blog> page = blogService.listBlogByTypeId(pageNum, id);
        int buttonCount = 5; // 分页按钮数量
        int startPage = Math.max(1, (int)page.getCurrent() - buttonCount / 2); // 计算起始页码
        int endPage = Math.min(startPage + buttonCount - 1, (int)page.getPages()); // 计算结束页码

        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList()); // 生成页码列表


        model.addAttribute("pageNumbers", pageNumbers); // 分页号码;
        model.addAttribute("blogs", page.getRecords()); // 类型列表;
        model.addAttribute("pageInfo", page);
        model.addAttribute("prePage", (page.getCurrent() - (page.hasPrevious() ? 1 : 0)));
        model.addAttribute("nextPage", (page.getCurrent() + (page.hasNext() ? 1 : 0)));
        model.addAttribute("type", id);
        return "types";
    }
}
