package com.example.demo.utils;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    // 自定义密钥（注意上线需更安全 & 保密）
    private static final String SECRET_KEY = "used-market-secret-key-1234567890";

    // 有效期：7天（单位：毫秒）
    private static final long EXPIRATION = 1000 * 60 * 60 * 24 * 7;

    /**
     * 生成 JWT Token，默认只保存 userId
     */
    public static String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 验证 token 并返回用户 ID
     */
    public static Long getUserId(String token) {
        try {
            Claims claims = parseToken(token);
            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解析 token 获取 Claims
     */
    private static Claims parseToken(String token) throws ExpiredJwtException, JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 判断 token 是否过期或非法
     */
    public static boolean isValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}