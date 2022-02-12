package com.gec.spring.proxy;

/**
 * 真实主题  ,业务实例类
 * @author 徐沛鹏
 */
public class RealSubject implements Subject {


    @Override
    public void doSomething() {
        System.out.println("调用了核心的业务功能!");
    }
}
