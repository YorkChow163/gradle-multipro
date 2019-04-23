package com.zhouyu.securitydemo.handler;

import com.zhouyu.securitydemo.service.JwtUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:退出要清空salt
 * @Author: zhouyu
 * @Date: 2019/3/18 17:45
 */
@Component
public class JwtLogoutHandler implements LogoutHandler {
  private static Logger logger = LoggerFactory.getLogger(JwtLogoutHandler.class);


  @Autowired
  JwtUserService jwtUserService;


  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    logger.info("退出登录，删除jwt");
    Object details = authentication.getDetails();
    Object principal = authentication.getPrincipal();
    jwtUserService.deleteUserJwt();
  }
}
