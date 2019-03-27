package com.zhouyu.securitydemo.interceptor;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * @Description: 拦截所有的访问路径，获取相应的权限
 * @Date:2019/3/27 9:51
 * @Author:zhouyu
 */
public class MySecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return null;
    }

    /**
     * 使用该方法获取url所对应的权限，然后再调用授权管理器AccessDecisionManager鉴权
     * @return
     */
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return null;
    }
}
