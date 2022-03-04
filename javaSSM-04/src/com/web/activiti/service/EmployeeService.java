package com.web.activiti.service;

import com.web.activiti.pojo.*;

import java.util.List;

/**
 *
 */
public interface EmployeeService {
    /**
     * 根据用户名查询用户
     * 唯一约束
     *
     * @param username
     */

    Employee findUserByName(String username);

    /**
     * 查询当前登录人的上级主管
     *
     * @param mangerid
     * @return
     */
    Employee findManage(Long mangerid);


    /**
     * 根据用户id查询菜单
     */

    List<SysPermission> findMenuListByUserId(String usrId);

    /**
     * 根据用户id查询权限
     */
    List<SysPermission> findPermissionListByUserId(String userid);

    /**
     * 查询一级菜单
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
     * 查询用户角色
     *
     * @return
     */
    List<EmployeeCustom> findUserAndRoleList();


    /**
     * 根据用户id查询角色权限
     */
    SysRole findRoleAndPermissionListByUserId(String userId);

    /**
     * 查询出角色列表
     *
     * @return
     */
    List<SysRole> findRoleAndPermissionList();


    List<SysRole> findRoleAll();

    /**
     * 修改员工权限
     */
    void updateEmployeeRole(String roleId, String userId);

    /**
     * 根据员工级别查找角色
     */
    List<Employee> findEmployeeByLevel(String level);

    /**
     * 添加用户
     */
    void saveEmployee(Employee employee, SysUserRole sysUserRole);

    /**
     * 删除用户
     */
    void deleteUserAndRole(String name);

    /**
     * 根据角色ID查询该角色的的权限
     */
    List<SysPermission> findPermissionsByRoleId(String userId);

    /**
     * 角色添加权限
     */
    void addRolesAndPersionn(SysRole sysRole, int[] permissionIds);

    /**
     * 删除角色
     */
    void deleteRoles(String id);


    List<SysPermission> findAllPersions();

    /**
     * 添加权限
     */

    void addSysPermission(SysPermission sysPermission);

    /**
     * 修改角色权限
     */
    void updateRoleAndPermission(String roleId, Integer[] permissionIds);

}
