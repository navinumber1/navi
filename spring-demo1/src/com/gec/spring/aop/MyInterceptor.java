package com.gec.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * 切面类:拦截器类(对通用问题的抽象)
 * 主要用于实现共性问题,定义功能增强方法
 */
@Aspect //切面类描述
public class MyInterceptor {

    //前置增强  原数据
    @Before("execution(* com.gec.spring.service.*.*(..))")
    public void before(JoinPoint jp) {
        String methodName = jp.getSignature().getName();//获取被拦截的方法的方法名
        Object[] args = jp.getArgs();//被拦截方法的参数列表
        Object target = jp.getTarget();//获取拦截的目标对象(业务对象)
        System.out.println("前置对象 方法名 :" + methodName + "," + ",参数列表:" + Arrays.toString(args) + ",拦截对象:" + target);

    }

    //后置增强
    @AfterReturning(pointcut = "execution(* com.gec.spring.service.*.*(..))",returning = "returnValue")
    public void afterRetruning(JoinPoint jp, Object returnValue) {
        String methodName = jp.getSignature().getName();//获取被拦截的方法的方法名
        Object[] args = jp.getArgs();//被拦截方法的参数列表
        Object target = jp.getTarget();//获取拦截的目标对象(业务对象)
        System.out.println("后置对象 方法名 :" + methodName + "," + ",参数列表:" + Arrays.toString(args) + ",拦截对象:" + target + ",返回值" + returnValue);

    }

    //环绕增强
    //返回值就是被拦截的业务方法的返回值
    @Around("execution(* com.gec.spring.service.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //前置处理
        String methodName = pjp.getSignature().getName();//获取被拦截的方法的方法名
        Object[] args = pjp.getArgs();//被拦截方法的参数列表
        Object target = pjp.getTarget();//获取拦截的目标对象(业务对象)
        System.out.println("环绕-前置对象 方法名 :" + methodName + "," + ",参数列表:" + Arrays.toString(args) + ",拦截对象:" + target);

        //调用被拦截的核心业务方法
        Object retrunValue = pjp.proceed();
        //后置处理
        String methodName2 = pjp.getSignature().getName();//获取被拦截的方法的方法名
        Object[] args2 = pjp.getArgs();//被拦截方法的参数列表
        Object target2 = pjp.getTarget();//获取拦截的目标对象(业务对象)
        System.out.println("环绕-后置对象 方法名 :" + methodName2 + "," + ",参数列表:" + Arrays.toString(args2) + ",拦截对象:" + target2 + ",f返回值" + retrunValue);
        return retrunValue;
    }
    //异常增强
    @AfterThrowing(pointcut = "execution(* com.gec.spring.service.*.*(..))",throwing = "e")
    public  void  afterThrowing(JoinPoint jp, RuntimeException e){
        String methodName = jp.getSignature().getName();//获取被拦截的方法的方法名
        Object[] args = jp.getArgs();//被拦截方法的参数列表
        Object target = jp.getTarget();//获取拦截的目标对象(业务对象)
        System.out.println("异常增强 方法名 :" + methodName + "," + ",参数列表:" + Arrays.toString(args) + ",拦截对象:" + target);
        System.out.println("异常增强   信息: "+e.getMessage());
    }
}
