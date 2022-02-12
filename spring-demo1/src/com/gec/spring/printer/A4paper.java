package com.gec.spring.printer;

public class A4paper implements paper{
    @Override
    public String getSize() {
        return "A4纸张打印...";
    }
}
