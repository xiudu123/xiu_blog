package com.xiu.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiu.blog.pojo.Type;
import com.xiu.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
 * @date: 2023/6/26 11:49
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 实现分类的 curd
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    //分页查询
    @GetMapping("/types")
    public String getTypes(@RequestParam(defaultValue = "1") Integer pageNum, Model model){
        Page<Type> page = typeService.listType(pageNum);

        int buttonCount = 5; // 分页按钮数量
        int startPage = Math.max(1, (int)page.getCurrent() - buttonCount / 2); // 计算起始页码
        int endPage = Math.min(startPage + buttonCount - 1, (int)page.getPages()); // 计算结束页码

        List<Integer> pageNumbers = IntStream.rangeClosed(startPage, endPage)
                .boxed()
                .collect(Collectors.toList()); // 生成页码列表


        model.addAttribute("pageNumbers", pageNumbers); // 分页号码;
        model.addAttribute("types", page.getRecords()); // 类型列表;
        model.addAttribute("pageInfo", page);
        return "admin/types-manage";
    }

    //跳转到输入页面(新增)
    @GetMapping("/types/input")
    public String inputAddType(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    //跳转到输入页面(修改)
    @GetMapping("/types/{id}/input")
    public String inputUpdateType(@PathVariable int id, Model model, RedirectAttributes attributes){
        Type type = typeService.getType(id);
        if(type == null){
            attributes.addFlashAttribute("errorMessage","该分类不存在或已被删除");
            return "redirect:/admin/types";
        }
        model.addAttribute("type", type);
        return "admin/types-input";
    }

    //新增分类
    @PostMapping("/types")
    public String addType(Type type, RedirectAttributes attributes) {
        // 判断名称是否为空;
        if("".equals(type.getName()) || type.getName() == null){
            attributes.addFlashAttribute("errorMessage","请输入分类名称");
            return "redirect:/admin/types/input";
        }
        // 判断是否重复添加分类;
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
            attributes.addFlashAttribute("errorMessage","分类已存在");
            return "redirect:/admin/types/input";
        }

        Date date = new Date();
        type.setCreateTime(date);
        type.setUpdateTime(date);
        int successInsert = typeService.insertType(type);
        if(successInsert == 0) attributes.addFlashAttribute("errorMessage","添加失败");
        else  attributes.addFlashAttribute("successMessage","添加成功");
        return "redirect:/admin/types";
    }

    //修改分类
    @PostMapping("/types/{id}")
    public String updateType(@PathVariable int id, Type type, RedirectAttributes attributes) {
        Type oldType = typeService.getType(id);

        // 判断名称是否为空;
        if("".equals(type.getName()) || type.getName() == null){
            attributes.addFlashAttribute("errorMessage","请输入分类名称");
            return "redirect:/admin/types/{id}/input";
        }
        // 判断分类是否修改
        if(oldType.getName().equals(type.getName())){
            attributes.addFlashAttribute("errorMessage","请不要输入相同的分类");
            return "redirect:/admin/types/{id}/input";
        }
        // 判断是否重复添加分类;
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null){
            attributes.addFlashAttribute("errorMessage","分类已存在");
            return "redirect:/admin/types/{id}/input";
        }

        type.setUpdateTime(new Date());
        int successUpdate = typeService.updateType(id, type);
        if(successUpdate == 0) attributes.addFlashAttribute("errorMessage","修改失败");
        else  attributes.addFlashAttribute("successMessage","修改成功");
        return "redirect:/admin/types";
    }

    //删除分类
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable int id, RedirectAttributes attributes) {
        if(typeService.getType(id) == null){
            attributes.addFlashAttribute("errorMessage","该类型不存在或已被删除");
            return "redirect:/admin/types/input";
        }

        int successDelete = typeService.deleteType(id);
        if (successDelete == 0) attributes.addFlashAttribute("message", "删除失败,有博客绑定该分类！");
        else attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
