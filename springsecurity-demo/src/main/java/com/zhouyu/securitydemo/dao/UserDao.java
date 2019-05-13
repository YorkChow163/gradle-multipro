package com.zhouyu.securitydemo.dao;

import com.zhouyu.securitydemo.entity.sys.MyUser;
import org.springframework.data.repository.CrudRepository;

/**
 * @Description:
 * @Author: zhouyu
 * @Date: 2019/3/19 17:03
 */
public interface UserDao extends CrudRepository<MyUser,Long> {
    public MyUser findUserByUsername(String name);
}
