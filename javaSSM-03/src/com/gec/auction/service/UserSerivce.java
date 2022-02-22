package com.gec.auction.service;

import com.gec.auction.pojo.Auction;
import com.gec.auction.pojo.User;


public interface UserSerivce {
  /**
   * 用户登录
   */
  User login(String username, String password);
  /**
   * 用户注册
   */
  void addUser(User user);
  /**
   * 管理员修改
   */
  void updateUser(User user);


  boolean isExist(String username);

}
