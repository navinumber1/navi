package com.web.activiti.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户身份信息，存入session 由于tomcat将session会序列化在本地硬盘上，所以使用Serializable接口
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser implements java.io.Serializable {
    /**
     * 用户id（主键）
     */
    private Long userid;
    /**
     * 部门id
     */
    private Long managerid;
    /**
     * 权限等级
     */
    private String role;

    /**
     * 用户账户
     */
    private String username;
    /**
     * 菜单
     */
    private List<SysPermission> menus;
    /**
     * 权限
     */
    private List<SysPermission> permissions;
    /**
     * 一级菜单
     */
    private List<TreeMenu> menuTree;
    /**
     * 用户表
     */
    private Employee employees;

    private static final long serialVersionUID = 1L;
}
