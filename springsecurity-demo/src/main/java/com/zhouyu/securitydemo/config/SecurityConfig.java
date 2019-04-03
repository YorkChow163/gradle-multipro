package com.zhouyu.securitydemo.config;

import com.zhouyu.securitydemo.filter.JwtAuthenrizationFilter;
import com.zhouyu.securitydemo.filter.JwtAuthenticationFilter;
import com.zhouyu.securitydemo.handler.JwtLogoutHandler;
import com.zhouyu.securitydemo.interceptor.MySecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @Description:全局安全配置
 * @Date:2019/3/25 15:30
 * @Author:zhouyu
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)开启方法级别的安全注解
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    AccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    @Qualifier("jwtUserService")
    private UserDetailsService userDetailsService;

    @Autowired
    private FilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;

    @Autowired
    private AccessDecisionManager myAccessDecisionManager;

    /**
     * 注入加密
     * @return
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //在这里指定密码的加密方式，SpringSecutity5.0之后必须指定
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        //auth.authenticationProvider(authenticationProvider());
       /* auth.inMemoryAuthentication() //认证信息存储到内存中
                .passwordEncoder(passwordEncoder())
                .withUser("zhouyu").password(passwordEncoder().encode("123456")).roles("ADMIN");*/
    }


    /**
     * 默认使用的就是DaoAuthenticationProvider,在这里只是显示的写出来参考
     * @param http
     * @throws Exception
     */
   /* @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* http.authorizeRequests()
                .antMatchers("/statics/**").permitAll()
                .antMatchers("/login.html").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                //.formLogin().disable()
                //不需要session
                .sessionManagement().disable()
                //跨域允许
                .cors()
                .and()
                .headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
                new Header("Access-control-Allow-Origin","*"),
                new Header("Access-Control-Expose-Headers","Authorization"))))
                .and()
                //验证
                .addFilterAt(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                //认证
                .addFilterAt(new JwtAuthenrizationFilter(authenticationManager()), BasicAuthenticationFilter.class)
                //异常处理
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                //.accessDeniedPage()
                .and()
                //添加自定义的拦截器,实现动态的权限管理,注意addFilterAt()方法会在相同位置添加拦截器，会导致拦截两次
                .addFilterAt(getMySecurityInterceptor(), FilterSecurityInterceptor.class)
                *//*.authorizeRequests()
                .accessDecisionManager(myAccessDecisionManager)
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(
                            O fsi) {
                        fsi.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
                        return fsi;
                    }
                })
                .and()*//*
                .formLogin()
                //登录页面
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
                .logout()
                .addLogoutHandler(new JwtLogoutHandler())
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .and()
                .sessionManagement().disable();*/

        //设置过滤器
        http.authorizeRequests().antMatchers("/statics/**").permitAll()
                .and()
                .httpBasic()
                .and()
                .addFilterAt(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(getMySecurityInterceptor(), FilterSecurityInterceptor.class)
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login.html")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll();

    }

    /**
     * 跨域配置
     * @return
     */
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


   /**
     * 自定义的动态权限配置
     * securityMetadataSource:负责根据URL查询对应的角色，然后加入ConfigAttribute的集合中
     * decisionManager:负责将configAttributes集合和登录用户的authentication里面的集合权限信息进行对比,判断是具有权限
     */
    @Bean
    public MySecurityInterceptor getMySecurityInterceptor(){
        MySecurityInterceptor dynaSecurityInterceptor = new MySecurityInterceptor();
        dynaSecurityInterceptor.setAccessDecisionManager(myAccessDecisionManager);
        dynaSecurityInterceptor.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
        return dynaSecurityInterceptor;
    }
}
