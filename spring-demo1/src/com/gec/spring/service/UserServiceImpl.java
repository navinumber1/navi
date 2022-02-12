package com.gec.spring.service;

public class UserServiceImpl implements UserService {
    @Override
    public String finduserId(String userid) {
        return "查询编号为"+userid+"的用户";
    }

    @Override
    public String add(String userid) {
        System.out.println("调用了add方法");
        throw new RuntimeException("增加用户错误!!!");
    }
}
