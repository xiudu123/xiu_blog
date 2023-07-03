package com.xiu.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xiu.blog.pojo.Blog;
import com.xiu.blog.pojo.Type;
import com.xiu.blog.service.BlogService;
import com.xiu.blog.service.BlogTagService;
import com.xiu.blog.service.TagService;
import com.xiu.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: 锈渎
 * @date: 2023/6/26 2:53
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogTagService blogTagService;

    // 查询所有博客
    @GetMapping("blogs/manage")
    public String blogs(@RequestParam(defaultValue = "1") Integer pageNum, Blog blog, Model model){
        Page<Blog> blogPage =  blogService.listBlog(pageNum, blog);
        int buttonCount = 5; // 分页按钮数量
        int startPage = Math.max(1, (int)blogPage.getCurrent() - buttonCount / 2); // 计算起始页码
        int endPage = Math.min(startPage + buttonCount - 1, (int)blogPage.getPages()); // 计算结束页码
        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList()); // 生成页码列表


        model.addAttribute("pageNumbers", pageNumbers); // 分页号码;
        model.addAttribute("blogs", blogPage.getRecords());
        model.addAttribute("pageInfo", blogPage);

//        System.out.println("⬇-------------" + "@GetMapping(\"blogs/manage\")" +"--------------------⬇");
//        List<Blog> blogList = blogPage.getRecords();
//        for(Blog blog_ : blogList){
//            System.out.println(blog_);
//        }
//        System.out.println("⬆-------------" + "@GetMapping(\"blogs/manage\")" +"--------------------⬆");
        model.addAttribute("types", typeService.listTypeAll());
        model.addAttribute("tags", tagService.listTagAll());
        return "admin/blogs-manage";
    }

    // 按条件搜索博客
    @PostMapping("blogs/manage/search")
    public String search(@RequestParam(defaultValue = "1") Integer pageNum, Blog blogRule, Model model){
        Page<Blog> blogPage =  blogService.listBlog(pageNum, blogRule);
        int buttonCount = 5; // 分页按钮数量
        int startPage = Math.max(1, (int)blogPage.getCurrent() - buttonCount / 2); // 计算起始页码
        int endPage = Math.min(startPage + buttonCount - 1, (int)blogPage.getPages()); // 计算结束页码
        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList()); // 生成页码列表

//        System.out.println("⬇-------------" + " @PostMapping(\"blogs/manage/search\")" +"--------------------⬇");
//        System.out.println(blog);
//        List<Blog> blogList = blogPage.getRecords();
//        for(Blog blog_ : blogList){
//            System.out.println(blog_);
//        }
//        System.out.println("⬆-------------" + " @PostMapping(\"blogs/manage/search\")" +"--------------------⬆");
        model.addAttribute("pageNumbers", pageNumbers); // 分页号码;
        model.addAttribute("blogs", blogPage.getRecords());
        model.addAttribute("pageInfo", blogPage);
        return "admin/blogs-manage :: blogList";
    }

    @GetMapping("/blogs/input")
    public String inputAddBlog(Model model){
        model.addAttribute("types", typeService.listTypeAll());
        model.addAttribute("tags", tagService.listTagAll());
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }
    @GetMapping("/blogs/{id}/update")
    public String inputUpdateBlog(@PathVariable int id, Model model, RedirectAttributes attributes){
        Blog blog = blogService.getBlog(id);
        if(blog == null){
            attributes.addFlashAttribute("errorMessage","该博客不存在或已被删除");
            return "redirect:/admin/blogs/manage/search";
        }
        model.addAttribute("types", typeService.listTypeAll());
        model.addAttribute("tags", tagService.listTagAll());
        model.addAttribute("blog", blogService.getBlog(id));
        return "admin/blogs-update";
    }

    @PostMapping("/blogs/input/input")
    public String addBlog(Blog blog, HttpSession session, RedirectAttributes attributes){

        // 判断标题是否为空
        if("".equals(blog.getTitle()) || blog.getTitle() == null){
            attributes.addFlashAttribute("errorMessage", "请输入标题");
            return "redirect:/admin/blogs/input";
        }

        // 判断分类是否为空
        if(blog.getTypeId() == null || blog.getTypeId() < 1){
            attributes.addFlashAttribute("errorMessage", "请选择分类");
            return "redirect:/admin/blogs/input";
        }

        // 判断首图是否为空
        if("".equals(blog.getFirstPicture()) || blog.getFirstPicture() == null){
            attributes.addFlashAttribute("errorMessage", "请选择首图");
            return "redirect:/admin/blogs/input";
        }

        blog.setUserId(1);
        Date date = new Date();
        blog.setCreateTime(date);
        blog.setUpdateTime(date);

        blog.setViews(0);


        int successInsert = blogService.insertBlog(blog);
        if(successInsert == 0) attributes.addFlashAttribute("errorMessage","添加失败");
        else  {
            attributes.addFlashAttribute("successMessage","添加成功");
            int blogIg = blog.getId();
            List<Integer> tagIds = blog.getTagIds();
            blogTagService.insertBlogTag(blogIg, tagIds);
        }
        return "redirect:/admin/blogs/manage";
    }

    @PostMapping("/blogs/{id}/input")
    public String updateBlog(@PathVariable int id, Blog blog, HttpSession session, RedirectAttributes attributes){

        // 判断标题是否为空
        if("".equals(blog.getTitle()) || blog.getTitle() == null){
            attributes.addFlashAttribute("errorMessage", "请输入标题");
            return "redirect:/admin/blogs/{id}/input";
        }

        // 判断分类是否为空
        if(blog.getTypeId() == null || blog.getTypeId() < 1){
            attributes.addFlashAttribute("errorMessage", "请选择分类");
            return "redirect:/admin/blogs/{id}/input";
        }

        // 判断首图是否为空
        if("".equals(blog.getFirstPicture()) || blog.getFirstPicture() == null){
            attributes.addFlashAttribute("errorMessage", "请选择首图");
            return "redirect:/admin/blogs/{id}/input";
        }


        Blog oldBlog = blogService.getBlog(id);
        Date date = new Date();
        blog.setUserId(oldBlog.getUserId());
        blog.setViews(oldBlog.getViews());
        blog.setCreateTime(oldBlog.getCreateTime());
        blog.setUpdateTime(date);
        if(blog.getComment() == null) blog.setComment(false);
        if(blog.getTop() == null) blog.setTop(false);

        int successInsert = blogService.updateBlog(id, blog);
        if(successInsert == 0) attributes.addFlashAttribute("errorMessage","更新失败");
        else  {
            attributes.addFlashAttribute("successMessage","更新成功");
            blogTagService.deleteBlogTag(id);
            List<Integer> tagIds = blog.getTagIds();
            blogTagService.insertBlogTag(id, tagIds);
        }
        return "redirect:/admin/blogs/manage";
    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable int id, RedirectAttributes attributes){
        if(blogService.getBlog(id) == null){
            attributes.addFlashAttribute("errorMessage", "该博客不存在或已被删除");
            return "redirect:/admin/blogs/manage";
        }

        int successDelete = blogService.deleteBlog(id);
        if(successDelete == 0){
            attributes.addFlashAttribute("errorMessage", "删除失败");
        }else {
            attributes.addFlashAttribute("successMessage","删除成功");
            blogTagService.deleteBlogTag(id);
        }
        return "redirect:/admin/blogs/manage";
    }






}
