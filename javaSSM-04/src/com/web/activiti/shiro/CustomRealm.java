package com.web.activiti.shiro;

import com.web.activiti.pojo.ActiveUser;
import com.web.activiti.pojo.Employee;
import com.web.activiti.pojo.SysPermission;
import com.web.activiti.pojo.TreeMenu;
import com.web.activiti.service.EmployeeService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private EmployeeService employeeService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.获取账户(唯一)
        String username = (String) token.getPrincipal();
        Employee loginUser = employeeService.findUserByName(username);
        if (loginUser == null) {
            return null;
        }
        System.out.println(loginUser);
        String password_db = loginUser.getPassword();//密文
        System.out.println(password_db);
        String salt = loginUser.getSalt();
        //查询当前账户的菜单列表信息
        List<SysPermission> menus = employeeService.findMenuListByUserId(username);
        //查询所有菜单信息
        List<TreeMenu> menuList = employeeService.findMenuList();
        //封装认证用户的主题信息
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUserid(loginUser.getId());
        activeUser.setUsername(loginUser.getName());
        activeUser.setRole(loginUser.getRole());
        activeUser.setManagerid(loginUser.getManagerId());
        activeUser.setMenus(menus);
        activeUser.setMenuTree(menuList);
        activeUser.setEmployees(loginUser);
        //密码无需显示对比,有框架完成
        return new SimpleAuthenticationInfo(activeUser, password_db, ByteSource.Util.bytes(salt), "CustomRealm");
    }


    /**
     * 权限管理:主要用于加载合法用户所有的权限与角色的数据
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取主体
        ActiveUser activeUser = (ActiveUser) principalCollection.getPrimaryPrincipal();
        String username = activeUser.getUsername();
        //要结合权限数据库
        List<SysPermission> permissionList = employeeService.findPermissionListByUserId(username);

        List<String> permissions = new ArrayList<>();
        for (SysPermission sysPermission : permissionList) {
            permissions.add(sysPermission.getPercode());
            System.out.println(sysPermission.getPercode());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);
        return info;

    }
}
