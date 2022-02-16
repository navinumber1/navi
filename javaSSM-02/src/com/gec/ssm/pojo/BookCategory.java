package com.gec.ssm.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCategory implements Serializable {
    private Integer id; //主键

    private String name;//类别名称

    private static final long serialVersionUID = 1L;
}