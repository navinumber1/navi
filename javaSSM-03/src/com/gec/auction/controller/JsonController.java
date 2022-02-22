package com.gec.auction.controller;

import com.gec.auction.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JsonController {
    // @ResponseBody 把java对象转换为json对象
    // @RequestBody  把json对象转换为java对象
    @RequestMapping("/requestJson")
    @ResponseBody
    public User requestJson(@RequestBody User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getUserpassword());
        return user;
    }


    @RequestMapping("/requestNoJson")
    @ResponseBody
    public User requestNoJson(User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getUserpassword());
        return user;
    }
}
