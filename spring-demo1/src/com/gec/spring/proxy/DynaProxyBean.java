package com.gec.spring.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理类
 * @author 徐沛鹏
 */
public class DynaProxyBean implements InvocationHandler {

    private Object proxyobj; //代理的目标的对象

    private DynaProxyBean(Object proxyobj) {
        this.proxyobj = proxyobj;
    }

    /**
     *  静态工厂方法
     *  产生代理接口实例的工厂方法
     * @param obj  被代理的目标对象(原始产品)
     * @return  代理接口对象(增强后)
     */

    public static Object getProxyBean(Object obj) {
        //使用反射包中的api来实现
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),new DynaProxyBean(obj));
    }

    @Override //相当于调用业务的核心方法 (被增强后)  method对象表示被拦截的业务方法
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        preRequest();
        Object retrunValue = method.invoke(proxyobj,args);
        postRequest();
        return retrunValue;
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
