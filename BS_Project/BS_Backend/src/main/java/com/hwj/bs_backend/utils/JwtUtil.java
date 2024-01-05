package com.hwj.bs_backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component  // 添加此注解以使其成为Spring管理的组件
public class JwtUtil {

    //过期时间15分钟
    private static final long EXPIRE_TIME = 15 * 60 * 1000;

    // 从配置文件中读取密钥
    private static String TOKEN_SECRET;

    // 使用注入的值覆盖静态字段
    @Value("${jwt.secretKey}")
    public void setTokenSecret(String tokenSecret) {
        JwtUtil.TOKEN_SECRET = tokenSecret;
    }

    //生成签名,15分钟后过期
    public static String sign(int userId) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        // 使用密钥进行加密
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        //附带userID生成签名
        return JWT.create().withHeader(header).withClaim("userId", userId)
                .withExpiresAt(date).sign(algorithm);
    }

    //校验token
    public static boolean verity(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (IllegalArgumentException | JWTVerificationException e) {
            return false;
        }
    }

    // 从 token 中解析出 userId
    public static int getUserIdFromToken(String token) {
        try {
            return JWT.decode(token).getClaim("userId").asInt();
        } catch (JWTDecodeException e) {
            return -1;
        }
    }

}
