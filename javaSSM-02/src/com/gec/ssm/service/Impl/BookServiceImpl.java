package com.gec.ssm.service.Impl;

import com.gec.ssm.mapper.BookCategoryMapper;
import com.gec.ssm.mapper.BookMapper;
import com.gec.ssm.pojo.Book;
import com.gec.ssm.pojo.BookCategory;
import com.gec.ssm.pojo.BookExample;
import com.gec.ssm.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookCategoryMapper bookCategoryMapper;

    @Override
    public List<Book> queryBookAll(Integer categoryId) {
        BookExample example = new BookExample();
        BookExample.Criteria criteria = example.createCriteria();
        if (categoryId != null) {
            if (categoryId > 0 && categoryId.equals(categoryId)) {
                criteria.andCategoryIdEqualTo(categoryId);
            }
        }
        example.setOrderByClause("publish_time desc");
        return bookMapper.selectByExample(example);
    }


    @Override
    public List<BookCategory> queryBookCateGory() {
        return bookCategoryMapper.selectByExample(null);
    }

    @Override
    public void addBook(Book book) {
        bookMapper.insertSelective(book);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            bookMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public void updateBook(Book book) {
        bookMapper.updateByPrimaryKeySelective(book);
    }

    @Override
    public Book selectByIdBook(Integer id) {
        return bookMapper.selectByPrimaryKey(id);
    }
}
