package com.gec.ssm.controller;

import com.gec.ssm.pojo.Book;
import com.gec.ssm.pojo.BookCategory;
import com.gec.ssm.service.BookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
public class BookController {
    //控制器依赖于业务层
    @Autowired
    private BookService bookService;

    @RequestMapping("/qeryBook")
    public ModelAndView quertAll(Integer categoryId, @RequestParam(defaultValue = "1") Integer pageNum) {
        ModelAndView mv = new ModelAndView();
        //1.分页拦截 ,用于重构sql,生成分页sql语句
        PageHelper.startPage(pageNum,5);
        List<Book> books = bookService.queryBookAll(categoryId);
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        mv.addObject("bookList",books);
        List<BookCategory> booolist = bookService.queryBookCateGory();
        mv.addObject("booknameList", booolist);
        mv.addObject("categoryId",categoryId);
        mv.addObject("pageInfo",pageInfo);
        mv.addObject("bookList",books);
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping("/qeryname")
    public ModelAndView qeryname() {
        ModelAndView mv = new ModelAndView();
        List<BookCategory> booolist = bookService.queryBookCateGory();
        mv.addObject("booknameList", booolist);
        mv.setViewName("toBook");
        return mv;
    }

    @RequestMapping("/addBooksumbit")
    public String addBook(Book book) {
        book.setSelfTime(new Date());
        bookService.addBook(book);
        return "redirect:/qeryBook";
    }

    @RequestMapping("/deleteBook")
    public String deleteBook(Integer[] ids){
        bookService.deleteAll(ids);
        return "redirect:/qeryBook";
    }

    @RequestMapping("/updateBook")
    public String  updateBook(Book book){
        bookService.updateBook(book);
        return "redirect:/qeryBook";
    }
    @RequestMapping("/selectById")
    public ModelAndView  selectById(Integer id){
        ModelAndView mv = new ModelAndView();
        Book book = bookService.selectByIdBook(id);
        mv.addObject("book",book);
        mv.setViewName("toEdit");
        return mv;
    }
}
