package com.gec.spring.printer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *  打印机类 ,程序的主题结构
 */
public class Printer {
    private  Inkbox inkbox ;//向上转型
    private  paper paper;

    public Printer() {
    }

    public Printer(Inkbox inkbox, com.gec.spring.printer.paper paper) {
        this.inkbox = inkbox;
        this.paper = paper;
    }

    public Inkbox getInkbox() {
        return inkbox;
    }

    public void setInkbox(Inkbox inkbox) {
        this.inkbox = inkbox;
    }

    public com.gec.spring.printer.paper getPaper() {
        return paper;
    }

    public void setPaper(com.gec.spring.printer.paper paper) {
        this.paper = paper;
    }
    public void print (String content){
        System.out.println(this.inkbox.getColor());
        System.out.println(this.paper.getSize());
        System.out.println(content);
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/printer.xml");
        Printer printer = (Printer) context.getBean("printer");
        printer.print("hello worlsdsfwds");
    }
}
