package com.gec.spring.proxy;

/**
 * 静态代理
 */
public class ProxyBean implements Subject {
    //属性关系表示代理的关系
    private RealSubject realSubject;

    //构造器初始化对象
    public ProxyBean(RealSubject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public void doSomething() {
        preRequest();
        realSubject.doSomething();//原始产品核心功能
        postRequest();
    }

    //前置增强
    private void preRequest() {
        System.out.println("执行前,记录日志");
    }

    //后置增强
    private void postRequest() {
        System.out.println("执行后,释放资源");
    }
}
