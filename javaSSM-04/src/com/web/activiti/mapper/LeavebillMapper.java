package com.web.activiti.mapper;

import com.web.activiti.pojo.Leavebill;
import com.web.activiti.pojo.LeavebillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LeavebillMapper {
    long countByExample(LeavebillExample example);

    int deleteByExample(LeavebillExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Leavebill record);

    int insertOrUpdate(Leavebill record);

    int insertOrUpdateSelective(Leavebill record);

    int insertSelective(Leavebill record);

    List<Leavebill> selectByExample(LeavebillExample example);

    Leavebill selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Leavebill record, @Param("example") LeavebillExample example);

    int updateByExample(@Param("record") Leavebill record, @Param("example") LeavebillExample example);

    int updateByPrimaryKeySelective(Leavebill record);

    int updateByPrimaryKey(Leavebill record);

    int updateBatch(List<Leavebill> list);

    int updateBatchSelective(List<Leavebill> list);

    int batchInsert(@Param("list") List<Leavebill> list);
}