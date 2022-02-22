package com.gec.auction.service.impl;

import com.gec.auction.mapper.AuctionMapper;
import com.gec.auction.mapper.UserMapper;
import com.gec.auction.pojo.Auction;
import com.gec.auction.pojo.User;
import com.gec.auction.pojo.UserExample;
import com.gec.auction.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSerivceImpl implements UserSerivce {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        //拼接用户名和密码两个条件
        criteria.andUsernameEqualTo(username);
        criteria.andUserpasswordEqualTo(password);
        List<User> userList = userMapper.selectByExample(example);
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        user.setUserisadmin(0);
        userMapper.insertSelective(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public boolean isExist(String username) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> list = userMapper.selectByExample(example);
        return list != null && list.size() > 0;
    }

}
