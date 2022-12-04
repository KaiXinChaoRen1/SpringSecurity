package com.lwq.springsecurity.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * JWT工具类
 */
public class JwtHelper {

    //token过期时间
    private static long tokenExpiration = 365 * 24 * 60 * 60 * 1000;
    //加密秘钥
    private static String tokenSignKey = "123456";

    //根据用户id和用户名称生成token字符串
    public static String createToken(Long id) {
        String userId=String.valueOf(id);
        String token = Jwts.builder()
                .setSubject("AUTH-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .claim("userId", userId)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //从token字符串获取userid
    public static Long getUserId(String token) {
        try {
            if (StringUtils.isEmpty(token)) return null;

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            String userId =(String)claims.get("userId");
            return Long.valueOf(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    //测试使用
    public static void main(String[] args) {
        //加密获取token
        String token = JwtHelper.createToken(1l);
        System.out.println(token);
        //解密token获取信息
        Long userId = JwtHelper.getUserId(token);
        System.out.println(userId);
    }
}