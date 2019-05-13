package com.zhouyu.securitydemo.handler;

import com.zhouyu.securitydemo.entity.sys.MyUser;
import com.zhouyu.securitydemo.service.JwtUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:登录成功后的回调自定义实现
 * @Author: zhouyu
 * @Date: 2019/3/18 17:43
 */
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {
   private static final Logger logger = LoggerFactory.getLogger(JwtLoginSuccessHandler.class);

    JwtUserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MyUser user= (MyUser) authentication.getPrincipal();
        String token = "";
        logger.info("生成JWT:{}",token);
        response.setHeader("Authorization", token);
    }
}
