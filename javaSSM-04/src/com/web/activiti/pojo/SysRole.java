package com.web.activiti.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限角色表
 *
 * @author 徐沛鹏
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole implements Serializable {
    /**
     * id主键
     */
    private String id;
    /**
     * 角色
     */
    private String name;

    /**
     * 是否可用,1：可用，0不可用
     */
    private String available;
    /**
     * 权限角色表
     */
    private List<SysPermission> permissionList;

    private static final long serialVersionUID = 1L;
}