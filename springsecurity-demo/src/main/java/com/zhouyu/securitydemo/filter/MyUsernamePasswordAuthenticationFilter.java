package com.zhouyu.securitydemo.filter;

import com.alibaba.fastjson.JSONObject;
import com.zhouyu.securitydemo.entity.MyUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Description:springsecurity拦截用户的登录行为的filter,生成token
 * @Author: zhouyu
 * @Date: 2019/3/18 17:21
 */
public class MyUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    Logger logger = LoggerFactory.getLogger(getClass());

    public MyUsernamePasswordAuthenticationFilter() {
        //拦截url为 "/login" 的POST请求
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        //从json中获取username和password
        String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        String username = null, password = null;
        if (StringUtils.hasText(body)) {
            JSONObject jsonObj = JSONObject.parseObject(body);
            username = jsonObj.getString("name");
            password = jsonObj.getString("password");
        }
        if (username == null)
            username = "";
        if (password == null)
            password = "";
        username = username.trim();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
        logger.info("得到用户信息:{}",JSONObject.toJSONString(token));
        //封装后的token最终是交给provider来处理
        return this.getAuthenticationManager().authenticate(token);
    }

}
