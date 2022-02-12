package com.gec.spring.bean;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class TestScope {
    @Test
    public void test1(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/applicationContext.xml");
        Date date1 = (Date) context.getBean("date1");
        System.out.println(date1);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date date2 = (Date) context.getBean("date1");
        System.out.println(date2);
        System.out.println(date1==date2);
    }
    @Test
    public void test2(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/applicationContext.xml");
        Date date1 = (Date) context.getBean("date2");
        System.out.println(date1);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date date2 = (Date) context.getBean("date1");
        System.out.println(date2);
        System.out.println(date1==date2);
    }

}
