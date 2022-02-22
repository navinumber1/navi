package com.gec.auction.controller;

import com.gec.auction.pojo.Restlt;
import com.gec.auction.pojo.User;
import com.gec.auction.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserSerivce userSerivce;

    @RequestMapping("/doLogin")
    public String dologin(String username, String userpassword, Model model, HttpSession session, String inputCode) {
        //验证码判断
        if (!inputCode.equals(session.getAttribute("numrand"))) {
            model.addAttribute("errorMsg", "验证码错误!");
            return "login";
        }

        User login = userSerivce.login(username, userpassword);
        if (login != null) {
            //注册session
            session.setAttribute("user", login);
            return "redirect:/queryAuction"; //首页 重定向queryAuction
        } else {
            model.addAttribute("errorMsg", "用户或密码错误!");
            return "login";
        }
    }

    @RequestMapping("/doLogout")
    public String doLogout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    // BindingResult 表示存放错误消息的容器  并且紧跟在对象校验之后
    @RequestMapping("/register")
    public String doaddUser(Model model, @ModelAttribute("registerUser") @Validated User user, BindingResult bindingResult) {
        //1,数据校验
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors) {
                //错误消息放在作用域中
                model.addAttribute(error.getField(), error.getDefaultMessage());
            }
            return "register";
        }
        userSerivce.addUser(user);
        return "login";
    }

    @RequestMapping("/doupdateUser")
    public String doupdateUser(User user) {
        userSerivce.updateUser(user);
        return "redirect:/queryAuction";
    }

/*    @RequestMapping("/checkUserExist")
    @ResponseBody
    public Restlt checkUserExist(String username){
        Restlt restlt = new Restlt();
        System.out.println(username);
        boolean exist = userSerivce.isExist(username);
        if (exist){
              restlt.setMessage("用户名已占用");
        }else {
            restlt.setMessage("用户名可用");
        }
        return restlt;
    }
    */

    @RequestMapping("/checkUserExist")
    @ResponseBody
    public String checkUserExist(String username){
        System.out.println(username);
        boolean exist = userSerivce.isExist(username);
        return exist+"";
    }
}
