package com.zhouyu.securitydemo.service;

import com.zhouyu.securitydemo.dao.UserDao;
import com.zhouyu.securitydemo.entity.MyUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Description:UserDetailsService,实现自UserDetailsService
 * @Author: zhouyu
 * @Date: 2019/3/18 17:40
 */
public class JwtUserService implements UserDetailsService {

    private  static  Logger logger = LoggerFactory.getLogger(JwtUserService.class);

    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userDao.findUserByUsername(username);
        logger.info("查询到用户信息:{}",user.toString());
        return user;
    }
}
