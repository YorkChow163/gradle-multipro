package com.zhouyu.securitydemo.filter;

import com.zhouyu.securitydemo.cons.CommonConst;
import com.zhouyu.securitydemo.entity.MyUser;
import com.zhouyu.securitydemo.service.JwtUserService;
import com.zhouyu.securitydemo.util.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        UsernamePasswordAuthenticationToken authenticationToken = getToken(tokenHeader);
        //剩下的就交给authenticationManager、provider去做
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        super.doFilterInternal(request, response, chain);
    }
}
