package com.gec.ssm.mapper;

import com.gec.ssm.pojo.BookCategory;
import com.gec.ssm.pojo.BookCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookCategoryMapper {
    long countByExample(BookCategoryExample example);

    int deleteByExample(BookCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BookCategory record);

    int insertOrUpdate(BookCategory record);

    int insertOrUpdateSelective(BookCategory record);

    int insertSelective(BookCategory record);

    List<BookCategory> selectByExample(BookCategoryExample example);

    BookCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BookCategory record, @Param("example") BookCategoryExample example);

    int updateByExample(@Param("record") BookCategory record, @Param("example") BookCategoryExample example);

    int updateByPrimaryKeySelective(BookCategory record);

    int updateByPrimaryKey(BookCategory record);

    int updateBatch(List<BookCategory> list);

    int updateBatchSelective(List<BookCategory> list);

    int batchInsert(@Param("list") List<BookCategory> list);
}