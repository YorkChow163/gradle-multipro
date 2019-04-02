package com.zhouyu.securitydemo.filter;

import com.alibaba.fastjson.JSONObject;
import com.zhouyu.securitydemo.cons.CommonConst;
import com.zhouyu.securitydemo.entity.MyUser;
import com.zhouyu.securitydemo.globalmsg.BodyMsg;
import com.zhouyu.securitydemo.service.JwtUserService;
import com.zhouyu.securitydemo.util.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @Description:授权过滤器，验证用户的jwt并设置权限，主要解决"你能干什么"
 * @Author: zhouyu
 * @Date: 2019/3/18 17:21
 */
public class JwtAuthenrizationFilter extends BasicAuthenticationFilter {

    Logger LOGGER = LoggerFactory.getLogger(JwtAuthenrizationFilter.class);
    public JwtAuthenrizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * @param tokenHeader
     * @return
     */
    protected UsernamePasswordAuthenticationToken getToken(String tokenHeader) {
        LOGGER.info("Authenrization jwt:{}",tokenHeader);
        String token = tokenHeader.replace(CommonConst.TOKEN_PREFIX, "");
        MyUser user = JwtTokenUtils.getUserNameByToken(token);
        LOGGER.info("Authenrization username:{}",user.toString());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        String username = user.getUsername();
        UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        return passwordAuthenticationToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(CommonConst.JWTHEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(CommonConst.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        //有jwt则需要验证
        try {
            UsernamePasswordAuthenticationToken authenticationToken = getToken(tokenHeader);
            //剩下的就交给authenticationManager、provider去做
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            LOGGER.error("认证失败,reason:{}",e.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSONObject.toJSONString(new BodyMsg<>(e.getMessage(),403)));
        }
        super.doFilterInternal(request, response, chain);
    }

    /**
     * 认证成功
     * @param request
     * @param response
     * @param authResult
     * @throws IOException
     */
    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        super.onSuccessfulAuthentication(request, response, authResult);
    }

    /**
     * 认证失败
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     */
    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        LOGGER.error("authentication failed, reason:{}",failed.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(new BodyMsg<>(failed.getMessage(),403)));
    }
}
