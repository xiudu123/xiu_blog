package com.xiu.blog.controller.admin;

import com.xiu.blog.pojo.User;
import com.xiu.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author: 锈渎
 * @date: 2023/6/26 1:56
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 关于用户登录登出操作
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session, // 用于在服务器端存储和管理会话数据
                        RedirectAttributes attributes // 用于在重定向过程中传递参数
                        ){

        if("".equals(username) || username == null){
            attributes.addFlashAttribute("message", "用户名不能为空");
            return "redirect:/admin";
        }
        if("".equals(password) || password == null){
            attributes.addFlashAttribute("message", "密码不能为空");
            return "redirect:/admin";
        }
        User user = userService.checkUser(username, password);
        if(user == null){
            attributes.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:/admin";
        }

        user.setPassword(null);
        session.setAttribute("user", user);
        return "admin/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
