package com.gec.spring.aop;

import com.gec.spring.service.UserService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class TestAop {
    @Test
    public void test1() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/aop.xml");
        //必须使用接口类型接收,返回的是代理接口实例(功能增强后的业务实例)
        UserService userService = (UserService) context.getBean("userService");
        String s = userService.finduserId("U007");
        System.out.println(s);
    }

    @Test
    public void test2() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/aop.xml");
        //必须使用接口类型接收,返回的是代理接口实例(功能增强后的业务实例)
        UserService userService = (UserService) context.getBean("userService");
        String add = userService.add("1111");
        System.out.println(add);
    }

    @Test
    public void test3() {
        int[] arr = new int[]{99, 55, 411, 23, 15, 1, 365, 42};
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int k = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = k;
                }
            }
            System.out.println(Arrays.toString(arr));
        }
    }
}
