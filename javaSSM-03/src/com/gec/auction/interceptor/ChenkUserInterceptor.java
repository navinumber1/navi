package com.gec.auction.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChenkUserInterceptor implements HandlerInterceptor {
    //调用handler之前调用(拦截)
    //true:放行 进入目标handler
    //false:不放行,不会进入目标handler
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        //获取请求路径,检查被拦截的资源
        String path = request.getRequestURI();
        System.out.println(path);
        if (path.contains("doLogin") || path.contains("register")) {
            return true;
        }
        if (session.getAttribute("user") != null) {//已近登录,放行
            return true;
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return false;
        }

    }

    //进入handler方法,调用完核心代码,但没有返回 ModelAndView之前调用
    //应用场景,有修改 ModelAndView 的需求可以使用,在公用模块中集中处理 ModelAndView 的数据时

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {

    }

    //完全执行handler方法时调用,在retrun语句之后调用
    //应用场景: 记录后台日志信息
    @Override
    public void afterCompletion(HttpServletRequest requestt, HttpServletResponse response, Object handler, Exception e) throws Exception {

    }
}
