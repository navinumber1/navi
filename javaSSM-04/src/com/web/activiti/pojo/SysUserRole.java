package com.web.activiti.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色关系表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole implements Serializable {
    private String id;
    /**
     * 用户id
     */
    private String sysUserId;
    /**
     * 角色id
     */
    private String sysRoleId;

    private static final long serialVersionUID = 1L;
}