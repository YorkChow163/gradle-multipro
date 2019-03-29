package com.zhouyu.securitydemo;

import com.zhouyu.securitydemo.dao.PermissionDao;
import com.zhouyu.securitydemo.dao.RoleDao;
import com.zhouyu.securitydemo.dao.UserDao;
import com.zhouyu.securitydemo.entity.MyPermission;
import com.zhouyu.securitydemo.entity.MyUser;
import com.zhouyu.securitydemo.entity.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringSecurityDemoApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringSecurityDemoApplicationTest {

    @Autowired
    RoleDao roleDao;

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDao userDao;

    /**
     * 无法自动插入中间表的保存
     */
    @Test
    public void insertMockData(){
        //资源权限
        MyPermission permission = permission();
        MyPermission permissionDb = permissionDao.save(permission);


        //角色
        ArrayList<MyPermission> myPermissions = new ArrayList<>();
        myPermissions.add(permissionDb);
        Role role = makeRoles();
        roleDao.save(role);


        //用户
        MyUser user = makeUser();
        userDao.save(user);
    }

    /**
     * 可以自动插入中间表的保存
     */
    @Test
    public void insertMockData2(){
        //资源权限
        MyPermission permission = permission();

        //角色
        ArrayList<MyPermission> myPermissions = new ArrayList<>();
        myPermissions.add(permission);
        Role role = makeRoles();
        role.setPermissions(myPermissions);

        //用户
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        MyUser user = makeUser();
        user.setRoles(roles);
        userDao.save(user);
    }

    private MyUser makeUser(){
        MyUser user = new MyUser();
        String encode = passwordEncoder.encode("123456");
        user.setPassword(encode);
        user.setUsername("zhouyu");
        user.setInsertUid(1);
        user.setEmail("2171677178@qq.com");
        return user;
    }

    private Role makeRoles(){
        Role role = new Role();
        role.setAuthority("Root");
        role.setCode("root");
        role.setDescpt("最高指挥官");
        role.setInsertUid(1);
        return role;
    }

    private MyPermission permission(){
        MyPermission myPermission = new MyPermission();
        myPermission.setPage("/index");
        myPermission.setDescpt("去首页url");
        myPermission.setName("首页");
        myPermission.setCode("indexPage");
        myPermission.setPid(0);
        return  myPermission;
    }



}