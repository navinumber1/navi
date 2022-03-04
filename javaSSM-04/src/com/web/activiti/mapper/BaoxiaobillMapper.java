package com.web.activiti.mapper;

import com.web.activiti.pojo.Baoxiaobill;
import com.web.activiti.pojo.BaoxiaobillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BaoxiaobillMapper {
    long countByExample(BaoxiaobillExample example);

    int deleteByExample(BaoxiaobillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Baoxiaobill record);

    int insertOrUpdate(Baoxiaobill record);

    int insertOrUpdateSelective(Baoxiaobill record);

    int insertSelective(Baoxiaobill record);

    List<Baoxiaobill> selectByExample(BaoxiaobillExample example);

    Baoxiaobill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Baoxiaobill record, @Param("example") BaoxiaobillExample example);

    int updateByExample(@Param("record") Baoxiaobill record, @Param("example") BaoxiaobillExample example);

    int updateByPrimaryKeySelective(Baoxiaobill record);

    int updateByPrimaryKey(Baoxiaobill record);

    int updateBatch(List<Baoxiaobill> list);

    int updateBatchSelective(List<Baoxiaobill> list);

    int batchInsert(@Param("list") List<Baoxiaobill> list);
}