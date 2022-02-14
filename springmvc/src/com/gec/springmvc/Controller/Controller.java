package com.gec.springmvc.Controller;


import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controller implements org.springframework.web.servlet.mvc.Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mv = new ModelAndView(); //模型数据+视图
        mv.addObject("msg","这是一个springmvc测试");
        mv.setViewName("index"); //设置视图名
        return mv;
    }
}
