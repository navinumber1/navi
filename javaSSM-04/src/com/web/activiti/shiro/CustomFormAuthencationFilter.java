package com.web.activiti.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CustomFormAuthencationFilter extends FormAuthenticationFilter {

    @Override  //如果返回true,终止请求,不会在调用subject.login
    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws Exception {
        HttpServletRequest request = (HttpServletRequest) req;
        //获取图片的验证码
        String validateCode = (String) request.getSession().getAttribute("validateCode");
        System.out.println("validateCode = " + validateCode);
        //获取用户输入的验证码
        String parameter = request.getParameter("randomcode");
        if (validateCode != null && parameter != null && !validateCode.equals(parameter)) {
             request.setAttribute(DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,"invalidateCodeError");
            return true;
        }
        return super.onAccessDenied(req, resp);  //执行默认的操作,subject.login
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.getAndClearSavedRequest(request);
        WebUtils.redirectToSavedRequest(request,response,"/first");
        return false;
    }
}
