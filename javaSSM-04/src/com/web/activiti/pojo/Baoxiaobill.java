package com.web.activiti.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 我的报销表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Baoxiaobill implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 申请金额
     */
    private Double money;
    /**
     * 标题
     */
    private String title;
    /**
     * 备注
     */
    private String remark;

    /**
     * 申请日期
     */
    private Date creatdate;
    /**
     * 申请事务进度
     */
    private Integer state;
    /**
     * 外键员工表id
     */
    private Integer userId;
    private static final long serialVersionUID = 1L;
}