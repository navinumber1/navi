package com.gec.springmvc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller  //@Component 让springmvc容器创建实例,并托管,作用类似<bean/>
public class TestController {

    @RequestMapping("/getTest") //请求映射
    public ModelAndView getTest() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", "注解的方式测试springmvc");
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping("/getTest2")
    public String getTest2(Model model) { //接口注入
        model.addAttribute("msg", "model保存的数据");
        return "index";
    }

    //默认接口类型的参数绑定
    @RequestMapping("/getTest3")
    public String getTest3(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        request.setAttribute("mike", "Mike in Request scope");
        session.setAttribute("mike", "Mike in Session scope");
        return "index";
    }

    //限制get提交方法
    @RequestMapping(value = "/getTest4", method = RequestMethod.GET)
    public String getTest4(int age, String username) {
        System.out.println(age);
        System.out.println(username);
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        return "index";
    }

    @RequestMapping("/getTime")
    public String getTime(Date birthday, String[] hobbies) {
        System.out.println(birthday);
        for (String hobby : hobbies) {
            System.out.println(hobby);
        }
        return "index";
    }

    @RequestMapping("/getPathParmeter/{age}/{username}")
    public String getPathParameter(@PathVariable int age, @PathVariable String username) {
        System.out.println(age);
        System.out.println(username);
        return "index";
    }
}
