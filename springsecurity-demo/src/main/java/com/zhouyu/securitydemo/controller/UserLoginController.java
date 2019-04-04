package com.zhouyu.securitydemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:用户登录退出业务
 * @Date:2019/3/25 15:30
 * @Author:zhouyu
 */
@Controller
public class UserLoginController {
   private static Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @RequestMapping("/login")
    public String goLoginPage(){
        logger.info("去登陆页面!!!");
        return "login";
    }

    /**
     * 获取验证码
     * @return
     */
    @RequestMapping(name = "/captcha",method = RequestMethod.GET)
    @ResponseBody
    public String getCaptcha(@RequestParam(value = "ID",required = false) String ID){
        return "zjpdghg";
    }

    @RequestMapping("/index")
    @ResponseBody
    public String gotoIndexPage(){
        logger.info("去首页!!!");
        return "zhouyu";
    }
}
