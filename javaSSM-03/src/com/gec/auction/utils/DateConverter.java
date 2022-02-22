package com.gec.auction.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 徐沛鹏
 */
public class DateConverter implements Converter<String, Date> {
    SimpleDateFormat[] sdf = {
            new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"),
            new SimpleDateFormat("yyyy年MM月dd日"),
            new SimpleDateFormat("yyyy/MM/dd")
    };


    @Override
    public Date convert(String time) {
        for (int i = 0; i < sdf.length; i++) {
            try {
                return sdf[i].parse(time);
            } catch (ParseException e) {
                //e.printStackTrace();
                continue;
            }
        }
        //throw new IllegalArgumentException();  //报400
        return null;  //不会的报400，请求还会进入到handler方法中执行。
    }

}
