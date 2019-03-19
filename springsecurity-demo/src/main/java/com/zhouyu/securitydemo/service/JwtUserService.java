package com.zhouyu.securitydemo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zhouyu.securitydemo.dao.UserDao;
import com.zhouyu.securitydemo.entity.MyUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;

/**
 * @Description:UserDetailsService从数据库或者内存获取用户信息
 * @Author: zhouyu
 * @Date: 2019/3/18 17:40
 */
public class JwtUserService implements UserDetailsService {

    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO 从数据库中加载用户密码，这里写死
        //User.builder().username("Jack").password(new PasswordEncoder().encode("jack-password")).roles("USER").build();
        MyUser user = userDao.findUserByUsername(username);
        return user;
    }

    /**
     * 根据用户信息，生成JWT
     * @param user 用户信息
     * @return
     */
    public String makeJWT(UserDetails user){
        //每个用户都有一只盐值，加盐
        String salt = "123456ef"; //BCrypt.gensalt();  正式开发时可以调用该方法实时生成加密的salt
        /**
         * @todo 将salt保存到数据库或者缓存中
         * redisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);
         */
        Algorithm algorithm = Algorithm.HMAC256(salt);
        Date date = new Date(System.currentTimeMillis()+3600*1000);  //设置JWT过期时间
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(date)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }
}
