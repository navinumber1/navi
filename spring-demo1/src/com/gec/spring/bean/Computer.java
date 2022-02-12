package com.gec.spring.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 电脑类
 *
 * @author 徐沛鹏
 */
public class Computer {
    private Host host;
    private Display display;

    public Computer() {
    }

    public Computer(Host host, Display display) {
        this.host = host;
        this.display = display;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public void running() {
        this.host.run();
        this.display.run();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config/applicationContext.xml");
        Computer computer = (Computer) context.getBean("computer");
        computer.running();
    }
}
