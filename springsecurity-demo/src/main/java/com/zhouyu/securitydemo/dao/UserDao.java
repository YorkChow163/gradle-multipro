package com.zhouyu.securitydemo.dao;

import com.zhouyu.securitydemo.entity.MyUser;
import com.zhouyu.securitydemo.entity.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: zhouyu
 * @Date: 2019/3/19 17:03
 */
@Component
public class UserDao {
    public MyUser findUserByUsername(String name) {
        MyUser user = new MyUser();
        user.setUsername("zhouyu");
        user.setPassword("123456");
        Role role = new Role();
        role.setName("ROOT");
        role.setDescpt("最高指挥官");
        role.setCode("root");
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        return user;
    }
}
