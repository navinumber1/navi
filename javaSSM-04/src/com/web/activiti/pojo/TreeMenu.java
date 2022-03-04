package com.web.activiti.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 菜单封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeMenu implements java.io.Serializable{
    /**
     * 一级菜单
     */
    private int id;

    private String name;
    /**
     * 二级菜单
     */
    private List<SysPermission> children;

}
