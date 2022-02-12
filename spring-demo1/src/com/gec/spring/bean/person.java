package com.gec.spring.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class person {
    private String name;
    private String speaking;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeaking() {
        return speaking;
    }

    public void setSpeaking(String speaking) {
        this.speaking = speaking;
    }

    public void show() {
        System.out.println(this.name + ",说:" + this.speaking);
    }

    public static void main(String[] args) {
        //先获取spring的ioc容器
        ApplicationContext context = new ClassPathXmlApplicationContext("config/applicationContext.xml");
       person person = (person) context.getBean("person");
       person.show();
    }
}
