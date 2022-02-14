package com.gec.springmvc.converter;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author 徐沛鹏
 */
public class DateConverter  implements Converter<String, Date> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年MM月dd日");


    @Override
    public Date convert(String time) {
        try {

            return sdf.parse(time);
        } catch (ParseException e) {
            // e.printStackTrace();
        }
        try {

            return sdf2.parse(time);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        try {

            return sdf3.parse(time);
        } catch (ParseException e) {
           // e.printStackTrace();
        }
        //转换失败，如果允许程序继续 运行，可以进入handler，则返回null
        //return null;
       //报400错误，不会进入handler中。错误会抛给前端控制器，在页面直接报错，程序终止。
        throw new IllegalArgumentException();
    }


//    @Override
//    public Date convert(String time) {
//        Date date = null;
//        SimpleDateFormat sdf = getDateFormat(time);
//        try {
//                //字符串转换成日期格式
//                date = sdf.parse(time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return date;
//    }
//    //利用正则表达式控制日期的输入格式
//    public SimpleDateFormat getDateFormat(String source) {
//        SimpleDateFormat sf = new SimpleDateFormat();
//        if (Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", source)) {
//                sf = new SimpleDateFormat("yyyy-MM-dd");
//        }
//        if (Pattern.matches("^\\d{4}/\\d{2}/\\d{2}$", source)) {
//                sf = new SimpleDateFormat("yyyy/MM/dd");
//        }
//        if (Pattern.matches("^\\d{4}\\d{2}\\d{2}$", source)) {
//                sf = new SimpleDateFormat("yyyy年MM月dd日");
//        }
//        return sf;
//    }
}
