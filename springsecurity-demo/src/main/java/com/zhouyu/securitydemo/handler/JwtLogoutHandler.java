package com.zhouyu.securitydemo.handler;

import com.zhouyu.securitydemo.service.JwtUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:退出要清空salt
 * @Author: zhouyu
 * @Date: 2019/3/18 17:45
 */
public class JwtLogoutHandler implements LogoutHandler {

    JwtUserService jwtUserService;

    public JwtLogoutHandler(JwtUserService jwtUserService) {
        this.jwtUserService = jwtUserService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Object details = authentication.getDetails();
        Object principal = authentication.getPrincipal();
        jwtUserService.deleteUserJwt();
    }
}
