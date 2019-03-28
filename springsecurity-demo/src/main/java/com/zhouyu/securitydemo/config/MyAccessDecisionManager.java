package com.zhouyu.securitydemo.config;

import com.zhouyu.securitydemo.cons.CommonConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @Description:权限管理器，匹配权限，进行验证
 * @Date:2019/3/27 10:10
 * @Author:zhouyu
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    private static Logger logger = LoggerFactory.getLogger(MyAccessDecisionManager.class);

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if(CollectionUtils.isEmpty(configAttributes)){
            logger.warn("AccessDecision your configAttributes is empty");
            throw new AccessDeniedException("configAttributes empty");
        }
        for (ConfigAttribute configAttribute: configAttributes) {
            //获取角色
            String role = configAttribute.getAttribute();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority:authorities) {
                if(authority.getAuthority().equals(role)||authentication.equals(CommonConst.ANONYMOUS)){
                    logger.info("匹配到相应的权限,role:{}",role);
                    return;
                }
            }
        }
        //登录用户没有匹配到对应权限,则禁止访问
        throw new AccessDeniedException("not allow");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
