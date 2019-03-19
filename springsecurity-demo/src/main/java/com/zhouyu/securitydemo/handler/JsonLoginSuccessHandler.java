package com.zhouyu.securitydemo.handler;

import com.zhouyu.securitydemo.entity.MyUser;
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
 * @Description:登录成功后的回调，这里我们把token加工一下成为JWT，然后随着fileter设置到请求头里面返回给客户端，下次客户端就会持有此JWT来到资源服务器请求资源
 * @Author: zhouyu
 * @Date: 2019/3/18 17:43
 */
public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {
   private static final Logger logger = LoggerFactory.getLogger(JsonLoginSuccessHandler.class);

    JwtUserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MyUser user= (MyUser) authentication.getPrincipal();
        String token = userService.makeJWT(user);
        logger.info("生成JWT:{}",token);
        response.setHeader("Authorization", token);
    }
}
