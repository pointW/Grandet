package com.grandet.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by outen on 16/7/2.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        //查看评论放行
        if(request.getServletPath().startsWith("comment")&&"GET".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        //如果用户已经登录 放行
        if(request.getSession().getAttribute("currentUser") != null) {
            System.out.println("已经登录");
            return true;
        }

        //非法请求 即这些请求需要登录后才能访问
        //重定向
        response.sendRedirect("/api/loginFirst");
        return false;
    }
}

