package com.zhouyu.securitydemo.service;

import com.zhouyu.securitydemo.dao.UserDao;
import com.zhouyu.securitydemo.entity.sys.MyUser;
import com.zhouyu.securitydemo.entity.sys.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @Description:UserDetailsService,实现自UserDetailsService
 * @Author: zhouyu
 * @Date: 2019/3/18 17:40
 */
@Service
public class JwtUserService implements UserDetailsService {

    private  static  Logger LOGGER = LoggerFactory.getLogger(JwtUserService.class);

    @Autowired
    UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 从数据库中查询用户，密码应该是数据库加密的密码，但是这里和登录的时候一致，使用写死的密码
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userDao.findUserByUsername(username);
        if(user==null){
            user.setUsername("zhouyu");
            user.setPassword("123456");
            Role role = new Role();
            role.setAuthority("ROOT");
            role.setDescpt("最高指挥官");
            role.setCode("root");
            ArrayList<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
        }
        String password = passwordEncoder.encode("123456");
        user.setPassword(password);
        LOGGER.info("查询到用户信息:{}",user.toString());
        return user;
    }


    /**
     * 用户注册
     * @param user
     */
    public  void regisUser(MyUser user){

    }

    public void deleteUserJwt(){
        //TODO从Redis中删除jwt

    }
}
