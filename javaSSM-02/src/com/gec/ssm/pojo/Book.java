package com.gec.ssm.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {
    private Integer id;//主键

    private String name; // 图书名称

    private Integer categoryId; //图书类别id   外键约束

    private Date publishTime;//出版时间

    private BigDecimal price; // 价格

    private String publishName; //出版社名称

    private Date selfTime;//上架时间

    private static final long serialVersionUID = 1L;
}