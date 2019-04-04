package com.zhouyu.securitydemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 在这设置视图层,登录的login跳转到templates下的页面 https://blog.csdn.net/xiaoxiaomao123098/article/details/86188752
 * @Date:2019/4/3 17:17
 * @Author:zhouyu
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer{

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        System.out.println("登录试图设置");
//        registry.addViewController("/login").setViewName("login");
    }
}
