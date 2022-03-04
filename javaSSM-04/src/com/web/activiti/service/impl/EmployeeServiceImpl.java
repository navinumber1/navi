package com.web.activiti.service.impl;

import com.web.activiti.mapper.*;
import com.web.activiti.pojo.*;
import com.web.activiti.service.EmployeeService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author 徐沛鹏
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private SysPermissionMapperCustom sysPermissionMapperCustom;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;


    @Override
    public Employee findUserByName(String username) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(username);
        List<Employee> list = employeeMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Employee findManage(Long mangerid) {
        return employeeMapper.selectByPrimaryKey(mangerid);
    }

    @Override
    public List<SysPermission> findMenuListByUserId(String usrId) {
        return sysPermissionMapperCustom.findMenuListByUserId(usrId);
    }

    @Override
    public List<SysPermission> findPermissionListByUserId(String userid) {
        return sysPermissionMapperCustom.findPermissionListByUserId(userid);
    }

    @Override
    public List<TreeMenu> findMenuList() {
        return sysPermissionMapperCustom.findMenuList();
    }

    @Override
    public List<SysPermission> getSubMenu(String id) {
        return sysPermissionMapperCustom.getSubMenu(id);
    }

    @Override
    public List<TreeMenu> findPermission() {
        return sysPermissionMapperCustom.findPermission();
    }

    @Override
    public List<SysPermission> findPermissionSys(String id) {
        return sysPermissionMapperCustom.findPermissionSys(id);
    }

    @Override
    public List<EmployeeCustom> findUserAndRoleList() {
        return sysPermissionMapperCustom.findUserAndRoleList();
    }

    @Override
    public SysRole findRoleAndPermissionListByUserId(String userId) {
        return sysPermissionMapperCustom.findRoleAndPermissionListByUserId(userId);
    }

    @Override
    public List<SysRole> findRoleAndPermissionList() {
        return sysPermissionMapperCustom.findRoleAndPermissionList();
    }

    @Override
    public List<SysRole> findRoleAll() {
        SysRoleExample example = new SysRoleExample();
        SysRoleExample.Criteria criteria = example.createCriteria();
        criteria.andAvailableEqualTo("1");
        return sysRoleMapper.selectByExample(example);
    }

    @Override
    public void updateEmployeeRole(String roleId, String userId) {

        SysUserRoleExample example = new SysUserRoleExample();
        SysUserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andSysUserIdEqualTo(userId);
        SysUserRole userRole = userRoleMapper.selectByExample(example).get(0);
        userRole.setSysRoleId(roleId);
        userRoleMapper.updateByPrimaryKey(userRole);
    }

    @Override
    public List<Employee> findEmployeeByLevel(String level) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andRoleEqualTo(level);
        List<Employee> list = employeeMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public void saveEmployee(Employee employee, SysUserRole sysUserRole) {
        //String salt = "eteokues";
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        String password = new Md5Hash(employee.getPassword(), salt, 3).toString();
        System.out.println(password);
        employee.setPassword(password);
        employee.setSalt(salt);
        employeeMapper.insertSelective(employee);
        sysUserRole.setSysUserId(employee.getName());
        sysUserRole.setSysRoleId(employee.getRole());
        String uuid = UUID.randomUUID().toString();
        sysUserRole.setId(uuid);
        userRoleMapper.insertSelective(sysUserRole);
    }

    @Transactional
    @Override
    public void deleteUserAndRole(String name) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        employeeMapper.deleteByExample(example);
    }

    @Override
    public List<SysPermission> findPermissionsByRoleId(String userId) {
        return sysPermissionMapperCustom.findPermissionsByRoleId(userId);
    }

    @Override
    public void addRolesAndPersionn(SysRole sysRole, int[] permissionIds) {
        sysRoleMapper.insertSelective(sysRole);
        //添加角色和权限关系表
        for (int permissionId : permissionIds) {
            String uuid = UUID.randomUUID().toString();
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setId(uuid);
            rolePermission.setSysRoleId(sysRole.getId());
            rolePermission.setSysPermissionId(permissionId + "");
            sysRolePermissionMapper.insert(rolePermission);
        }
    }

    @Override
    public void deleteRoles(String id) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        sysRole.setAvailable("0");
        sysRoleMapper.updateByPrimaryKey(sysRole);
    }

    @Override
    public List<SysPermission> findAllPersions() {
        SysPermissionExample sysPermissionExample = new SysPermissionExample();
        sysPermissionExample.createCriteria().andTypeEqualTo("menu");
        return sysPermissionMapper.selectByExample(sysPermissionExample);
    }

    @Override
    public void addSysPermission(SysPermission sysPermission) {
        sysPermissionMapper.insertSelective(sysPermission);
    }

    @Override
    public void updateRoleAndPermission(String roleId, Integer[] permissionIds) {
        SysRolePermissionExample example = new SysRolePermissionExample();
        SysRolePermissionExample.Criteria criteria = example.createCriteria();
        criteria.andSysRoleIdEqualTo(roleId);
        sysRolePermissionMapper.deleteByExample(example);
        for (Integer pid : permissionIds) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            String uuid = UUID.randomUUID().toString();
            sysRolePermission.setId(uuid);
            sysRolePermission.setSysRoleId(roleId);
            sysRolePermission.setSysPermissionId(pid.toString());
            sysRolePermissionMapper.insertSelective(sysRolePermission);
        }
    }
}
