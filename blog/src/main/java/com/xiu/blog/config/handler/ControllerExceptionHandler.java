package com.xiu.blog.config.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 锈渎
 * @date: 2023/6/24 0:46
 * @code: 面向对象面向君， 不负代码不负卿。
 * @description: 自定义错误拦截器, 同一处理页面错误判断
 */

@ControllerAdvice // 拦截所有标注有Controller注解的错误
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //定义这是一个异常处理类方法Exception异常都会经过该方法
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandel(HttpServletRequest request,Exception e) throws Exception {
        //错误信息打印成日志
        logger.error("Request URL: {} Exception : {}",request.getRequestURL(),e);

        //当标识了状态码的时候就不拦截,抛出交给虚拟机处理如：404,500等
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }

        //定义返回的视图
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }
}
