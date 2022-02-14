package com.gec.springmvc.zuoye;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ControllerDemo {
    @RequestMapping(value = "/getDemo", method = RequestMethod.POST)
    public ModelAndView getDemo(Users user) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        mv.setViewName("submit");
        System.out.println(user);
        return mv;
    }

    @RequestMapping(value = "/getDemo2", method = RequestMethod.POST)
    public String getDemo2(HttpSession session, Users users) {
        session.setAttribute("user", users);
        System.out.println(users);
        return "submit";
    }
}
