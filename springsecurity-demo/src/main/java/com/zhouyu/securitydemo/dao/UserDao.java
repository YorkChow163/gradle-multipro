package com.zhouyu.securitydemo.dao;

import com.zhouyu.securitydemo.entity.MyUser;

/**
 * @Description:
 * @Author: zhouyu
 * @Date: 2019/3/19 17:03
 */
public interface UserDao {
   MyUser findUserByUsername(String name);
}
