package com.web.activiti.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 账户名称
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 权限等级
     */
    private String role;
    /**
     * 部门id
     */
    private Long managerId;
    /**
     *
     */

    private String salt;
    /**
     * 盐
     */
    private static final long serialVersionUID = 1L;
}