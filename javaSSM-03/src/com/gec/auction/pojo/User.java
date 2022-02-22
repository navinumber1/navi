package com.gec.auction.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
     * 用户表
     */
    private Integer userid;
    @Size(min = 3, max = 8, message = "{user.username.length.error}")
    private String username;
    @Size(min = 6, message = "{user.password.length.error}")
    private String userpassword;
    @Pattern(regexp = "\\d{18}", message = "{user.usercardno.format.error}")
    private String usercardno;
    @Size(min = 7,message = "{user.usertel.length.error}")
    private String usertel;

    private String useraddress;

    private String userpostnumber;

    private Integer userisadmin;

    private String userquestion;

    private String useranswer;

    private static final long serialVersionUID = 1L;
}