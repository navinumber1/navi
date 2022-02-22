package com.gec.auction.controller;


import com.gec.auction.utils.AuctionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 增强版的controller,主要用于全局数据处理
 */
@ControllerAdvice
public class MyException {

    @ExceptionHandler(AuctionException.class)
     public ModelAndView priceException(Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorMsg", e.getMessage());
        mv.setViewName("error");
        return mv;
    }

}
