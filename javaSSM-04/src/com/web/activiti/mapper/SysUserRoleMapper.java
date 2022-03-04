package com.web.activiti.mapper;

import com.web.activiti.pojo.SysUserRole;
import com.web.activiti.pojo.SysUserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserRoleMapper {
    long countByExample(SysUserRoleExample example);

    int deleteByExample(SysUserRoleExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysUserRole record);

    int insertOrUpdate(SysUserRole record);

    int insertOrUpdateSelective(SysUserRole record);

    int insertSelective(SysUserRole record);

    List<SysUserRole> selectByExample(SysUserRoleExample example);

    SysUserRole selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysUserRole record, @Param("example") SysUserRoleExample example);

    int updateByExample(@Param("record") SysUserRole record, @Param("example") SysUserRoleExample example);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    int updateBatch(List<SysUserRole> list);

    int updateBatchSelective(List<SysUserRole> list);

    int batchInsert(@Param("list") List<SysUserRole> list);
}