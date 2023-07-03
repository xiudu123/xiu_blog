package com.xiu.blog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiu.blog.pojo.Tag;
import com.xiu.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: 锈渎
 * @date: 2023/6/28 12:43
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 标签的 curd
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    //分页查询
    @GetMapping("/tags")
    public String getTag(@RequestParam(defaultValue = "1") Integer pageNum, Model model){
        Page<Tag> page = tagService.listTag(pageNum);

        int buttonCount = 5; // 分页按钮数量
        int startPage = Math.max(1, (int)page.getCurrent() - buttonCount / 2); // 计算起始页码
        int endPage = Math.min(startPage + buttonCount - 1, (int)page.getPages()); // 计算结束页码
        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList()); // 生成页码列表


        model.addAttribute("pageNumbers", pageNumbers); // 分页号码;
        model.addAttribute("tags", page.getRecords()); // 类型列表;
        model.addAttribute("pageInfo", page);
        return "admin/tags-manage";
    }

    //跳转到输入页面(新增)
    @GetMapping("/tags/input")
    public String inputAddTag(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    //跳转到输入页面(修改)
    @GetMapping("/tags/{id}/input")
    public String inputUpdateTag(@PathVariable int id, Model model, RedirectAttributes attributes){
        Tag type = tagService.getTag(id);
        if(type == null){
            attributes.addFlashAttribute("errorMessage","该标签不存在或已被删除");
            return "redirect:/admin/tags";
        }
        model.addAttribute("tag", type);
        return "admin/tags-input";
    }

    //新增标签
    @PostMapping("/tags")
    public String addTag(Tag tag, RedirectAttributes attributes) {
        // 判断名称是否为空;
        if("".equals(tag.getName()) || tag.getName() == null){
            attributes.addFlashAttribute("errorMessage","请输入标签名称");
            return "redirect:/admin/tags/input";
        }
        // 判断是否重复添加分类;
        Tag oldTag = tagService.getTagByName(tag.getName());
        if(oldTag != null){
            attributes.addFlashAttribute("errorMessage","标签已存在");
            return "redirect:/admin/tags/input";
        }

        Date date = new Date();
        tag.setCreateTime(date);
        tag.setUpdateTime(date);
        int successInsert = tagService.insertTag(tag);
        if(successInsert == 0) attributes.addFlashAttribute("errorMessage","添加失败");
        else  attributes.addFlashAttribute("successMessage","添加成功");
        return "redirect:/admin/tags";
    }

    //修改标签
    @PostMapping("/tags/{id}")
    public String updateTag(@PathVariable int id, Tag tag, RedirectAttributes attributes) {
        Tag oldTag = tagService.getTag(id);

        // 判断名称是否为空;
        if("".equals(tag.getName()) || tag.getName() == null){
            attributes.addFlashAttribute("errorMessage","请输入标签名称");
            return "redirect:/admin/tags/{id}/input";
        }
        // 判断分类是否修改
        if(oldTag.getName().equals(tag.getName())){
            attributes.addFlashAttribute("errorMessage","请不要输入相同的标签");
            return "redirect:/admin/tags/{id}/input";
        }
        // 判断是否重复添加分类;
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null){
            attributes.addFlashAttribute("errorMessage","标签已存在");
            return "redirect:/admin/tags/{id}/input";
        }

        tag.setUpdateTime(new Date());
        int successUpdate = tagService.updateTag(id, tag);
        if(successUpdate == 0) attributes.addFlashAttribute("errorMessage","修改失败");
        else  attributes.addFlashAttribute("successMessage","修改成功");
        return "redirect:/admin/tags";
    }

    //删除标签
    @GetMapping("/tags/{id}/delete")
    public String deleteType(@PathVariable int id, RedirectAttributes attributes) {
        if(tagService.getTag(id) == null){
            attributes.addFlashAttribute("errorMessage","该类型不存在或已被删除");
            return "redirect:/admin/tags/input";
        }

        int successDelete = tagService.deleteTag(id);
        if (successDelete == 0) attributes.addFlashAttribute("message", "删除失败,有博客绑定该标签！");
        else attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

}
