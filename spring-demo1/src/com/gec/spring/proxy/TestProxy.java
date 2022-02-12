package com.gec.spring.proxy;

import com.gec.spring.service.UserService;
import com.gec.spring.service.UserServiceImpl;
import org.junit.Test;

public class TestProxy {
    @Test
    public  void test1(){
        ProxyBean proxyBean = new ProxyBean(new RealSubject());
        proxyBean.doSomething();
    }
    @Test
    public  void test2(){
        Subject proxyBean = (Subject) DynaProxyBean.getProxyBean(new RealSubject());
        proxyBean.doSomething();
    }
    @Test
    public void  test3(){
        UserService proxyBean = (UserService) DynaProxyBean.getProxyBean(new UserServiceImpl());
        String s = proxyBean.finduserId("111");
        System.out.println(s);
    }
}
