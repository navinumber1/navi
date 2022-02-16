package com.gec.ssm.service;

import com.gec.ssm.pojo.Book;
import com.gec.ssm.pojo.BookCategory;

import java.util.List;

public interface BookService {
    /**
     * 查询图书数据
     * 模糊数据
     * @param categoryId
     * @return
     */

    List<Book> queryBookAll(Integer categoryId);


    /**
     * 查询图书类型名称
     */
    List<BookCategory> queryBookCateGory();


    /**
     * 添加图书
     * @param book
     */
    public void addBook(Book book);

    /**
     * 批量删除
     */
    public void deleteAll(Integer[] ids);

    /**
     * 修改图书信息
     */
    public void updateBook(Book book);

    /**
     * 根据主键查询单行数据
     */
    public Book selectByIdBook(Integer id);

}
