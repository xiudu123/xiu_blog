package com.xiu.blog.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: 锈渎
 * @date: 2023/6/26 3:05
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description:  网页拦截
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**") // 拦截admin后面的路径
                .excludePathPatterns("/admin", "/admin/login") // 放行路径
                .excludePathPatterns("/css/**", "/img/**", "/js/**", "/lib/**", "/images/**"); // 放行静态资源
    }
}

