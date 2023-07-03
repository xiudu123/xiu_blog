package com.xiu.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: 锈渎
 * @date: 2023/7/2 12:38
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:
 */
@Controller
public class AboutController {

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
