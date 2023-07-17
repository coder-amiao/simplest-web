package cn.soboys.restapispringbootstarter.utils;

import cn.soboys.restapispringbootstarter.authorization.UserSign;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/13 20:09
 * @webSite https://github.com/coder-amiao
 */
public class JwtUtil {

    //创建jwt
    public static String createJWT(String subject, String issue, Object claim,
                                   long ttlMillis) {


        //过期时间
        Date now = new Date();
        Date exp = new Date(now.getTime() + ttlMillis * 1000);


        String result = Jwts.builder()
                .setSubject(subject) //设置主题
                .setIssuer(issue) //发行者
                .setId(issue)//jwtID
                .setExpiration(exp)//设置过期日期
                .claim("user", claim)//主题，可以包含用户信息
                .signWith(getSignatureAlgorithm(), getAuthKey())//加密算法
                .compressWith(CompressionCodecs.DEFLATE).compact();//对载荷进行压缩
        return result;
    }


    public static String createJWT(String subject, String issue, Object claim,
                                   long ttlMillis, SignatureAlgorithm signatureAlgorithm, String key) {

        //过期时间
        Date now = new Date();
        Date exp = new Date(now.getTime() + ttlMillis * 1000);

        String result = Jwts.builder()
                .setSubject(subject) //设置主题
                .setIssuer(issue) //发行者
                .setId(issue)//jwtID
                .setExpiration(exp) //设置过期日期
                .claim("user", claim)//主题，可以包含用户信息
                .signWith(signatureAlgorithm, key)//加密算法
                .compressWith(CompressionCodecs.DEFLATE).compact();//对载荷进行压缩

        return result;
    }


    // 解析jwt
    public static Jws<Claims> parseJWT(String jwt) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(getSignedKey())
                .parseClaimsJws(jwt);
//        try {
//            claims = Jwts.parser().setSigningKey(getSignedKey())
//                    .parseClaimsJws(jwt);
//        } catch (Exception ex) {
//            claims = null;
//        }
        return claims;
    }

    public static Jws<Claims> parseJWT(String jwt, Key key) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(jwt);
        return claims;
    }


    //获取主题信息
    public static Claims getClaims(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(getSignedKey())
                .parseClaimsJws(jwt).getBody();

        return claims;
    }


    public static Claims getClaims(String jwt, Key key) {
        Claims claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }


    /**
     * 获取密钥
     *
     * @return Key
     */
    private static Key getSignedKey() {
        byte[] apiKeySecretBytes = DatatypeConverter
                .parseBase64Binary(getAuthKey());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,
                getSignatureAlgorithm().getJcaName());
        return signingKey;
    }


    /**
     * 自定义秘钥
     *
     * @return
     */
    public static String getAuthKey() {
        String auth = "2af57b969bac152d";
        return auth;
    }

    /**
     * 自定义签名
     *
     * @return
     */
    private static SignatureAlgorithm getSignatureAlgorithm() {
        return SignatureAlgorithm.HS256;
    }


}
