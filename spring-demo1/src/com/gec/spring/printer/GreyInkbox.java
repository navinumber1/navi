package com.gec.spring.printer;

public class GreyInkbox implements Inkbox{
    @Override
    public String getColor() {
        return "黑白打印....";
    }
}
