package com.zhouyu.securitydemo.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhouyu.securitydemo.globalmsg.BodyMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:认证未通过处理
 * @Date:2019/4/2 14:40
 * @Author:zhouyu
 * AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
 * AccessDeineHandler 用来解决认证过的用户访问无权限资源时的异常
 */
@Component
public class JwtAccessDeniedHandler  implements AccessDeniedHandler {
    private  static Logger LOGGER = LoggerFactory.getLogger(JwtAccessDeniedHandler.class);
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        LOGGER.error("你无权访问，虽然你已经验证,reason：{}",accessDeniedException.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(new BodyMsg<>(accessDeniedException.getMessage(),403)));
    }
}
