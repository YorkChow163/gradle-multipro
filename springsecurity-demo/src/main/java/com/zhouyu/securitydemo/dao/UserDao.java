package com.zhouyu.securitydemo.dao;

import com.zhouyu.securitydemo.entity.MyUser;
import com.zhouyu.securitydemo.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: zhouyu
 * @Date: 2019/3/19 17:03
 */
public interface UserDao extends CrudRepository<MyUser,Long> {
    public MyUser findUserByUsername(String name);
}
