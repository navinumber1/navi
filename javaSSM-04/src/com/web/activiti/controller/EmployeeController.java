package com.web.activiti.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.web.activiti.pojo.*;
import com.web.activiti.service.EmployeeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        String errorException = (String) request.getAttribute("shiroLoginFailure");
        if (errorException != null) {
            //1.账户错误
            if (UnknownAccountException.class.getName().equals(errorException)) { //登录成功
                model.addAttribute("errorMsg", "账户错误");
            } else {
                model.addAttribute("errorMsg", "密码错误");
            }
            if ("invalidateCodeError".equals(errorException)) {
                model.addAttribute("errorMsg", "验证码错误");
            }

        }
        if (activeUser != null) {
            return "redirect:/first";
        }
        return "login";
    }

    //系统首页
    @RequestMapping("/first")
    public String first(Model model, HttpSession session) throws Exception {
        //从shiro的session中取activeUser
        Subject subject = SecurityUtils.getSubject();
        //取身份信息
        ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
        //通过model传到页面
        model.addAttribute("activeUser", activeUser);
        session.setAttribute("activeUser", activeUser);
        return "index";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    public static final Integer PAGESIZE = 7;

    @RequestMapping("/findUserList")
    public ModelAndView findUserList(@RequestParam(defaultValue = "1") Integer pageNum) {
        ModelAndView mv = new ModelAndView();
        PageHelper.startPage(pageNum, PAGESIZE);
        List<EmployeeCustom> userAndRoleList = employeeService.findUserAndRoleList();
        if (userAndRoleList != null && userAndRoleList.size() > 0) {
            PageInfo<EmployeeCustom> info = new PageInfo<>(userAndRoleList);
            mv.addObject("userList", userAndRoleList);
            mv.addObject("pageInfo", info);
        }
        List<SysRole> roleAll = employeeService.findRoleAll();
        mv.addObject("allRoles", roleAll);
        mv.setViewName("userlist");
        return mv;
    }

    /**
     * 根据用户查询权限
     */
    @RequestMapping("/viewPermissionByUser")
    @ResponseBody
    public SysRole viewPermissionByUser(String userName) {
        return employeeService.findRoleAndPermissionListByUserId(userName);
    }

    @RequestMapping("/assignRole")
    @ResponseBody //将java类型转换为json格式
    public Map<String, String> assignRole(String roleId, String userId) {
        Map<String, String> map = new HashMap<>();
        try {
            employeeService.updateEmployeeRole(roleId, userId);
            map.put("msg", "分配权限成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "分配权限失败");
        }
        return map;
    }

    @RequestMapping("/findMaxManager")
    @ResponseBody
    public List<Employee> findMaxManager(long level) {
        return employeeService.findEmployeeByLevel(String.valueOf(level));
    }

    @RequestMapping("/checkName")
    @ResponseBody
    public Integer checkName(String username) {
        Employee userByName = employeeService.findUserByName(username);
        if (userByName == null) {
            return 1;
        }
        return 0;
    }

    @RequestMapping("/saveUser")
    public String saveUser(Employee employee, SysUserRole sysUserRole) {
        employeeService.saveEmployee(employee, sysUserRole);
        return "redirect:/findUserList";
    }

    @RequestMapping("/deleteUserAndRole")
    public String deleteUserAndRole(String name) {
        employeeService.deleteUserAndRole(name);
        return "redirect:/findUserList";
    }

    @RequestMapping("/toAddRole")
    public ModelAndView toAddRole() {
        ModelAndView mv = new ModelAndView();
        List<TreeMenu> menuList = employeeService.findPermission();
        List<SysPermission> allPersions = employeeService.findAllPersions();
        mv.addObject("allPermissions", menuList);
        mv.addObject("menuTypes", allPersions);
        mv.setViewName("rolelist");
        return mv;
    }

    @RequestMapping("/saveRoleAndPermissions")
    public String saveRoleAndPermissions(SysRole sysRole, int[] permissionIds,Model model) {
        if (permissionIds==null){
            model.addAttribute("msg","权限不能为空");
            return "redirect:/toAddRole";
        }
        String uuid = UUID.randomUUID().toString();
        sysRole.setAvailable("1");
        sysRole.setId(uuid);
        employeeService.addRolesAndPersionn(sysRole, permissionIds);
        return "redirect:/toAddRole";
    }

    @RequestMapping("/saveSubmitPermission")
    public String saveSubmitPermission(SysPermission sysPermission) {
        employeeService.addSysPermission(sysPermission);
        return "redirect:/toAddRole";
    }


    @RequestMapping("/findRoles")
    public ModelAndView findRoles(@RequestParam(defaultValue = "1") Integer pageNum) {
        ModelAndView mv = new ModelAndView();
        PageHelper.startPage(pageNum, PAGESIZE);
        List<SysRole> roleAll = employeeService.findRoleAll();
        PageInfo<SysRole> pageInfo = new PageInfo<>(roleAll);
        List<TreeMenu> menuList = employeeService.findPermission();
        mv.addObject("allMenuAndPermissions", menuList);
        mv.addObject("pageInfo", pageInfo);
        mv.addObject("allRoles", roleAll);
        mv.setViewName("permissionlist");
        return mv;
    }

    @RequestMapping("/loadMyPermissions")
    @ResponseBody
    public List<SysPermission> loadMyPermissions(String roleId) {
        return employeeService.findPermissionsByRoleId(roleId);
    }

    @RequestMapping("/updateRoleAndPermission")
    public String updateRoleAndPermission(String roleId, Integer[] permissionIds){
        employeeService.updateRoleAndPermission(roleId,permissionIds);
        return "redirect:/findRoles";
    }


    @RequestMapping("/deleteRoles")
    public String deleteRoles(String id) {
        employeeService.deleteRoles(id);
        return "redirect:/findRoles";
    }
}
