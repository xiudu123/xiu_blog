package com.xiu.blog.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: 锈渎
 * @date: 2023/6/26 3:00
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 拦截器，拦截未登录权限
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if(request.getSession().getAttribute("user") == null){
            response.sendRedirect("/admin");
            return false;
        }

        return true;
    }
}
