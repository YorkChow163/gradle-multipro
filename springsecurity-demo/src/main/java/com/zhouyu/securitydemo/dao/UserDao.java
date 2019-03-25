package com.zhouyu.securitydemo.dao;

import com.zhouyu.securitydemo.entity.MyUser;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zhouyu
 * @Date: 2019/3/19 17:03
 */
@Component
public class UserDao {
  public MyUser findUserByUsername(String name){
     MyUser user=new MyUser();
     user.setUsername("zhouyu");
     user.setPassword("123456");
     return  user;
   }
}
