package com.web.activiti.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 报销申请表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leavebill implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 请假日期
     */
    private Integer days;
    /**
     * 标题
     */
    private String content;
    /**
     * 备注
     */
    private String remark;
    /**
     * 申请时间
     */
    private Date leavedate;
    /**
     * 申请事务进程
     */
    private Integer state;
    /**
     * 外键员工表id
     */
    private Long userId;
    private static final long serialVersionUID = 1L;
}