package com.zhouyu.securitydemo.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @Description:用户登录退出业务
 * @Date:2019/3/25 15:30
 * @Author:zhouyu
 */
@Controller
public class UserLoginController {
   private static Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    DefaultKaptcha defaultKaptcha;

    @RequestMapping("/login")
    public String userLogin(){
        logger.info("登录啦");
        return "login";
    }

    @RequestMapping("/kaptcha")
    public void createKaptcha(@RequestParam(name = "uuid",required = false)String uuid, HttpServletResponse response) throws Exception{
        logger.info("获取验证码,uuid:{}",uuid);
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = defaultKaptcha.createText();
        //生成图片验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        //保存text 到 redis

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    @RequestMapping("/index")
    @ResponseBody
    public String gotoIndexPage(){
        logger.info("去首页");
        return "zhouyu";
    }
}
