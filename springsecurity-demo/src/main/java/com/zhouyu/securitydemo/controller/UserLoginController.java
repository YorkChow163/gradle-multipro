package com.zhouyu.securitydemo.controller;

import com.zhouyu.securitydemo.entity.MyUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:用户登录退出业务
 * @Date:2019/3/25 15:30
 * @Author:zhouyu
 */
@RestController
public class UserLoginController {
   private static Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @RequestMapping("/login")
    public String userLogin(@RequestBody MyUser user){
        logger.info("login,user:{}",user.toString());
        return "zhouyu";
    }

    @RequestMapping("/index")
    @ResponseBody
    public String gotoIndexPage(){
        logger.info("去首页");
        return "zhouyu";
    }
}
