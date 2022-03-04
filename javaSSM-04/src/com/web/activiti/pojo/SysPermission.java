package com.web.activiti.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限分配表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysPermission implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 权限资源名称
     */
    private String name;

    /**
     * 权限资源类型：menu,button,
     */
    private String type;

    /**
     * 权限访问url地址
     */
    private String url;

    /**
     * 权限代码字符串
     */
    private String percode;

    /**
     * 父结点id
     */
    private Long parentid;

    /**
     * 父结点id列表串
     */
    private String parentids;

    /**
     * 排序号
     */
    private String sortstring;

    /**
     * 是否可用,1：可用，0不可用
     */
    private String available;

    private static final long serialVersionUID = 1L;
}