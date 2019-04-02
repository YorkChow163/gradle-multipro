package com.zhouyu.securitydemo.filter;

import com.alibaba.fastjson.JSONObject;
import com.zhouyu.securitydemo.cons.CommonConst;
import com.zhouyu.securitydemo.entity.MyUser;
import com.zhouyu.securitydemo.globalmsg.BodyMsg;
import com.zhouyu.securitydemo.globalmsg.Exceptionenum;
import com.zhouyu.securitydemo.globalmsg.ReturnMsgEnum;
import com.zhouyu.securitydemo.util.JwtTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
 * @Description:验证过滤器,生成token返回，验证主要解决的是"你是谁"
 * @Author: zhouyu
 * @Date: 2019/3/19 16:40
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private  static  Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    private AuthenticationManager authenticationManager;

    /**
     * 在构造器中设置拦截的路劲,默认拦截的是"/login"
     * 在构造器中设置AuthenticationManager
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        this.authenticationManager=authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //从json中获取username和password
        UsernamePasswordAuthenticationToken token = null;
        try {
            String body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
            String username = null, password = null;
            if (StringUtils.hasText(body)) {
                JSONObject jsonObj = JSONObject.parseObject(body);
                username = jsonObj.getString("username");
                password = jsonObj.getString("password");
            }
            if (username == null){
                username = "";
            }
            if (password == null){
                password = "";
            }
            username = username.trim();
            token = new UsernamePasswordAuthenticationToken(username,password);
            LOGGER.info("get user info from login success,name:{}",token.getName());
        } catch (IOException e) {
            LOGGER.error("get user info from login failed，reason:{}",e.getMessage());
        }
        //封装后的token最终是交给provider来处理
        Authentication authenticate = authenticationManager.authenticate(token);
        return authenticate;
    }

    /**
     * 验证成功之后的回调,可以自己实现AuthenticationSuccessHandler处理(JwtLoginSuccessHandler)
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        MyUser user= (MyUser) authResult.getPrincipal();
        String jwt = JwtTokenUtils.createToken(user);
        StringBuffer buffer = new StringBuffer(CommonConst.TOKEN_PREFIX);
        buffer.append(jwt);
        logger.info(JSONObject.toJSONString(ReturnMsgEnum.AUTHENTICATIONSUCCESS));
        LOGGER.info("authentication success,user:【{}】,jwt:【{}】",user.toString(),buffer.toString());
        response.setHeader(CommonConst.JWTHEADER, buffer.toString());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(new BodyMsg<>(ReturnMsgEnum.AUTHENTICATIONSUCCESS)));
    }

    /**
     * 验证失败之后的回调，可以自己实现AuthenticationFailureHandler处理(JwtLoginFailureHandler)
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String message = failed.getMessage();
        LOGGER.error("authentication failed, reason:{}",message);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write( JSONObject.toJSONString(new BodyMsg<>(message,HttpStatus.FORBIDDEN.value())));
    }
}
