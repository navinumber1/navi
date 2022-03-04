package com.web.activiti.mapper;

import com.web.activiti.pojo.Employee;
import com.web.activiti.pojo.EmployeeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    int insertOrUpdate(Employee record);

    int insertOrUpdateSelective(Employee record);

    int insertSelective(Employee record);

    List<Employee> selectByExample(EmployeeExample example);

    Employee selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    int updateBatch(List<Employee> list);

    int updateBatchSelective(List<Employee> list);

    int batchInsert(@Param("list") List<Employee> list);
     List<Employee> findAllByEmailAfterAndEmail(@Param("minEmail")String minEmail,@Param("email")String email);


}