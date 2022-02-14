package com.gec.springmvc.zuoye;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
@Data
public class Users {
    private String username;//姓名
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;//生日
    private String sex;//性别
    private String education;//学历
    private String hobbies;//爱好

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getTime(){
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf3.format(birthday);
    }
}
