package com.zhouyu.securitydemo.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * @Description: 拦截所有的访问路径，进行动态权限校验
 * @Date:2019/3/27 9:51
 * @Author:zhouyu
 * 1.从SecurityContextHolder中获取 Authentication
 * 2.从SecurityMetadataSource中判断请求是否是公共开放的请求还是受保护的鉴权请求
 * 3.ConfigAttributes列表中维护了需要鉴权的请求
 */
public class MySecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private static Logger logger = LoggerFactory.getLogger(MySecurityInterceptor.class);

    @Override
    protected InterceptorStatusToken beforeInvocation(Object object) {
        logger.info("我的动态拦截器,object:{}", JSONObject.toJSONString(object));
        return super.beforeInvocation(object);
    }

    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) throws IOException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            /**
             * 这个方法调用securityMetadataSource来获取权限
             * 再使用decisionManager来判断权限
             */
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } catch (ServletException e) {
            logger.info("我的拦截器出现异常:{}",e.getMessage());
        }
        finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

    /**
     * 使用该方法获取url所对应的权限，然后再调用授权管理器AccessDecisionManager鉴权
     * @return
     * */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return securityMetadataSource;
    }
}
