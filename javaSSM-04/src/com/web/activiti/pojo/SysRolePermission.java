package com.web.activiti.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色权限关系表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRolePermission implements Serializable {

    private String id;

    /**
     * 角色id
     */
    private String sysRoleId;

    /**
     * 权限id
     */
    private String sysPermissionId;

    private static final long serialVersionUID = 1L;
}