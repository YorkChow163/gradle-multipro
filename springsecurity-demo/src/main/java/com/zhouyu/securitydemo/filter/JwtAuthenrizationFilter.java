package com.zhouyu.securitydemo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:授权过滤器，验证用户的jwt并设置权限，主要解决"你能干什么"
 * @Author: zhouyu
 * @Date: 2019/3/18 17:21
 */
public class JwtAuthenrizationFilter extends AbstractAuthenticationProcessingFilter {
    Logger logger = LoggerFactory.getLogger(getClass());

    public JwtAuthenrizationFilter() {
        //拦截url为 "/login" 的POST请求
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        return null;
    }

}
