package com.zhouyu.securitydemo.util;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zhouyu.securitydemo.entity.sys.MyUser;
import com.zhouyu.securitydemo.entity.sys.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtTokenUtils {
    private  static Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);

    /**
     * 加密盐值，实际上每个用户都有自己的盐值，生产环境需要放到redis中保存
     */
    private static String SECRET="zhouyu";

    /**
     * 创建jwt
     * @param user 用户
     * @return token字符串
     */
    public static String createToken(MyUser user) {
        //设置JWT过期时间
        Date date = new Date(System.currentTimeMillis()+36000*1000);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withIssuer("auth0")
                .withClaim("username",user.getUsername())
                .withClaim("roles", JSONObject.toJSONString(user.getAuthorities()))
                .withSubject(user.getUsername())
                .withExpiresAt(new Date())
                .withExpiresAt(date)
                .sign(algorithm);
        /**
         * @todo 将SECRET 保存到数据库或者缓存中
         * redisTemplate.opsForValue().set("token:"+token, SECRET, 3600, TimeUnit.SECONDS);
         */
    }


    /**
     * lwt是否已过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiresAt().before(new Date());
    }


    /**
     * 根据jwt获取用户名
     * @param token
     * @return
     */
    public static  MyUser getUserNameByToken(String token){
        Map<String, Claim> claims = getTokenBody(token).getClaims();
        String username =claims.get("username").asString();
        String roleJson = claims.get("roles").asString();
        List<Role> roles = JSONObject.parseArray(roleJson, Role.class);
        MyUser user = new MyUser();
        user.setUsername(username);
        user.setRoles(roles);
        logger.info("decode jwt user:{}",user.toString());
        return user;
    }

    public static DecodedJWT getTokenBody(String  token){
        /**
         * @todo 从Redis中获取每个用户的盐值SECRET
         * redisTemplate.get(token)
         */
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).withIssuer("auth0").build();
        DecodedJWT jwt = verifier.verify(token);
        return  jwt;
    }


}
