package com.web.activiti.utils;

import com.web.activiti.pojo.ActiveUser;
import com.web.activiti.pojo.Employee;
import com.web.activiti.service.EmployeeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


import javax.servlet.http.HttpSession;

/**
 * @author 徐沛鹏
 */
public class TaskexpressionUtils implements TaskListener {
    @Override
    public void notify(DelegateTask task) {
        // 通过硬编码获取spring容器
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        EmployeeService employeeService = (EmployeeService) context.getBean("employeeService");
        
        //获取当前登录人的信息 HttpSession
//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpSession session = servletRequestAttributes.getRequest().getSession();
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        Employee manage = employeeService.findManage(activeUser.getManagerid());
        //调用业务方法,查询当前任务人的上级主管
        task.setAssignee(manage.getName());
    }
}
