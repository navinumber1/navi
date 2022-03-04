package com.web.activiti.mapper;

import com.web.activiti.pojo.EmployeeCustom;
import com.web.activiti.pojo.SysPermission;
import com.web.activiti.pojo.SysRole;
import com.web.activiti.pojo.TreeMenu;

import java.util.List;

public interface SysPermissionMapperCustom {


    /**
     * 查询一级菜单
     *
     * @return
     */
    List<TreeMenu> findMenuList();

    /**
     * 二级菜单
     */
    List<SysPermission> getSubMenu(String id);


    /**
     * 查询一级菜单
     *
     * @return
     */
    List<TreeMenu> findPermission();

    /**
     * 二级菜单 三级权限
     */
    List<SysPermission> findPermissionSys(String id);


    /**
     * 根据id查询菜单
     *
     * @param userId
     * @return
     */
    List<SysPermission> findMenuListByUserId(String userId);

    /**
     * 根据用户id查询权限
     *
     * @param userid
     * @return
     */
    List<SysPermission> findPermissionListByUserId(String userid);

    /**
     * 连表查询用户信息显示用户信息以及用户用户的上级主管
     */
    List<EmployeeCustom> findUserAndRoleList();

    /**
     * 根据用户名称查询用户角色和对应的权限列表信息
     *
     * @param userId
     * @return
     */

    SysRole findRoleAndPermissionListByUserId(String userId);


    List<SysRole> findRoleAndPermissionList();


    List<TreeMenu> getAllMenuAndPermision();

    SysPermission getSubMenuAndPermissions(String id);
    /**
     * 根据角色ID查询该角色的的权限
     */
    List<SysPermission> findPermissionsByRoleId(String userId);
}






