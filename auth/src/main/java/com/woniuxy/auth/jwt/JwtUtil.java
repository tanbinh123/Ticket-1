package com.woniuxy.auth.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * 处理JWT的工具类
 *
 * @author 谭岚 (<a href="http://www.woniuxy.com">蜗牛学院</a>)
 */
@Slf4j
public class JwtUtil {

    public static String createJWT(int id, String account, Audience audience) {
        try {
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            // 生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(audience.getBase64Secret());
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            // userId是重要信息，进行加密下
//            String encryId = Base64Util.encode(userId);

            // 添加构成JWT的参数
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    // 可以将基本不重要的对象信息放到claims
                    .claim("account", account).claim("id", id)
                    // .setSubject(username) // 代表这个JWT的主体，即它的所有人
                    .setIssuer(audience.getClientId()) // 代表这个JWT的签发主体；
                    .setIssuedAt(new Date()) // 是一个时间戳，代表这个JWT的签发时间；
                    .setAudience(audience.getName()) // 代表这个JWT的接收对象；
                    .signWith(signatureAlgorithm, signingKey);
            // 添加Token过期时间
            int TTLMillis = audience.getExpiresSecond();
            if (TTLMillis >= 0) {
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp) // 是一个时间戳，代表这个JWT的过期时间；
                        .setNotBefore(now); // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
            }

            // 生成JWT
            return builder.compact();
        } catch (Exception e) {
            log.error("签名失败", e);
            // throw new CustomException(ResultCode.PERMISSION_SIGNATURE_ERROR);
        }
        return "";
    }

    public static boolean parseJwt(String jsonWebToken, String base64Security) {
        try {
            Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return true;
        } catch (ExpiredJwtException eje) {
            log.error("===== Token过期 =====", eje);
            // throw new RuntimeException();
            eje.printStackTrace();
            return false;
        } catch (Exception e) {
            log.error("===== token解析异常 =====", e);
//            throw new CustomException(ResultCode.PERMISSION_TOKEN_INVALID);
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 从Token获取用户的账号
     *
     * @param token
     * @param base64Security
     * @return
     */
    public static String getAccount(String token, String base64Security) {
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                .parseClaimsJws(token).getBody();
        return claims.get("account", String.class);
    }

    public static Integer getUserId(String token, String base64Security) {
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                .parseClaimsJws(token).getBody();
        return claims.get("id", Integer.class);
    }
}
