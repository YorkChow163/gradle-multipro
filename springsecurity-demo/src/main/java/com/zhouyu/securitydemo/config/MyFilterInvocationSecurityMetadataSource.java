package com.zhouyu.securitydemo.config;

import com.zhouyu.securitydemo.cons.CommonConst;
import com.zhouyu.securitydemo.dao.RoleDao;
import com.zhouyu.securitydemo.entity.MyPermission;
import com.zhouyu.securitydemo.entity.Role;
import com.zhouyu.securitydemo.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @Description:获取url,根据url来查询角色，最后返回给MyAccessDecisionManager进行鉴权
 * @Date:2019/3/27 11:24
 * @Author:zhouyu
 */
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Logger logger = LoggerFactory.getLogger(MyFilterInvocationSecurityMetadataSource.class);
    private  List<ConfigAttribute> configAttributes = new ArrayList<>();

    @Autowired
    PermissionService permissionService;

    @Autowired
    RoleDao roleDao;

    private PathMatcher matcher = new AntPathMatcher();

    @Value("${security.ignoring}")
    private String ignoreUrl;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        try {
            FilterInvocation fi = (FilterInvocation) object;
            logger.info("动态拦截url:{}",fi.getRequestUrl());
            String requestUrl = fi.getRequestUrl();

            //在这里可以配置不拦截的url
            List<String> ignoreUrls = getIgnoreUrl();
            for (String ignoreUrl : ignoreUrls) {
                if(matcher.match(ignoreUrl,requestUrl)){
                    ConfigAttribute attribute = new SecurityConfig(CommonConst.ANONYMOUS);
                    configAttributes.add(attribute);
                }
            }
            //如果没有改资源就保存
            MyPermission permission = permissionService.getPermission(fi.getRequestUrl());
            List<BigInteger> roleIds = roleDao.findAllByPermissionId(permission.getId());
            ArrayList<Long> ids = new ArrayList<>();
            for (BigInteger roleId : roleIds) {
                logger.info("*******:{}",roleId.longValue());
                ids.add(roleId.longValue());
            }
            Iterable<Role> roles = roleDao.findAllById(ids);
            roles.forEach(role -> {
                ConfigAttribute conf = new SecurityConfig(role.getCode());
                configAttributes.add(conf);
            });
        } catch (Exception e) {
           logger.error("拦截url获取权限失败,reason:{}",e.getMessage());
        }
        return configAttributes;
    }

    /**
     * 获取忽略的url
     * @return
     */
    private List<String> getIgnoreUrl(){
        String[] split = ignoreUrl.split(",");
        List<String> ignoreUrls = Arrays.asList(split);
        return  ignoreUrls;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return configAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
