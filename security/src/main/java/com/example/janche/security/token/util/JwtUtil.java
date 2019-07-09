package com.example.janche.security.token.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 * @author  daiyp
 * @date 2018-9-26
 */
public class JwtUtil {
    /**
     *
     * 功能：生成 jwt token<br/>
     * @param name	实例名
     * @param param	需要保存的参数
     * @param secret	秘钥
     * @param expirationtime	过期时间(5分钟 5*60*1000)
     * @return
     *
     */
    public static String sign(String name, Map<String,Object> param, String secret, Long expirationtime){
        String JWT = Jwts.builder()
                .setClaims(param)
                .setSubject(name)
                .setExpiration(new Date(System.currentTimeMillis() + expirationtime))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
        return JWT;
    }
    /**
     *
     * 功能：解密 jwt<br/>
     * @param JWT	token字符串
     * @param secret	秘钥
     * @return
     * @exception
     *
     */
    public static Claims verify(String JWT, String secret){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(JWT)
                .getBody();
        return claims;
    }

    public static String sign(Map<String,Object> param,String name){
        String JWT = Jwts.builder()
                .setClaims(param)
                .setSubject(name)
                .setExpiration(new Date(System.currentTimeMillis() + 100000))
                .signWith(SignatureAlgorithm.HS256,"SSSS")
                .compact();
        return JWT;
    }

    public static Claims verify(String JWT){
        Claims claims = Jwts.parser()
                // 验签
                .setSigningKey("SSSS")
                .parseClaimsJws(JWT)
                .getBody();
        return claims;
    }

    public static void main(String[] args){
        String str = sign(new HashMap<String,Object>(){{put("role","admin");put("create_time", new Date());}},"admin");
        System.out.println(str);
        System.out.println(verify("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJhZG1pbiIsImV4cCI6MTUyMjA1MjgwN30.G-JQyWqagtanyQLpYeveh44l8vPjiyK7z3-HamgZcbg"));

    }
}
