package com.zhouyu.securitydemo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * JWT工具类
 */
public class JwtTokenUtils {

    /**
     * 加密盐值，实际上每个用户都有自己的盐值，生产环境需要放到redis中保存
     */
    private static String SECRET="zhouyu";

    /**
     * 创建token
     * @param username 用户名
     * @return token字符串
     */
    public static String createToken(String username) {
        //设置JWT过期时间
        Date date = new Date(System.currentTimeMillis()+3600*1000);
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT.create()
                .withIssuer("auth0")
                .withClaim("username",username)
                .withSubject(username)
                .withExpiresAt(new Date())
                .withExpiresAt(date)
                .sign(algorithm);
        /**
         * @todo 将SECRET 保存到数据库或者缓存中
         * redisTemplate.opsForValue().set("token:"+token, SECRET, 3600, TimeUnit.SECONDS);
         */
    }


    /**
     * tokrn是否已过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiresAt().before(new Date());
    }


    /**
     * 获取用户名
     * @param token
     * @return
     */
    public static  String getUserNameByToken(String token){
        String username = getTokenBody(token).getClaim("username").asString();
        return username;
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
