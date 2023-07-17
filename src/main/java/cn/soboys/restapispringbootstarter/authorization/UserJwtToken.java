package cn.soboys.restapispringbootstarter.authorization;

import cn.soboys.restapispringbootstarter.Assert;
import cn.soboys.restapispringbootstarter.HttpStatus;
import cn.soboys.restapispringbootstarter.config.RestApiProperties;
import cn.soboys.restapispringbootstarter.exception.BusinessException;
import cn.soboys.restapispringbootstarter.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;

import io.jsonwebtoken.SignatureException;
import lombok.Data;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;

import java.util.LinkedHashMap;


/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/13 21:06
 * @webSite https://github.com/coder-amiao
 */
@Data
public class UserJwtToken {

    /**
     * 是否记住
     */
    public Boolean rememberMe = Boolean.FALSE;

    /**
     * 用户签名
     */
    private UserSign userSign;





    private RestApiProperties.JwtProperties jwtProperties = SpringUtil.getBean(RestApiProperties.JwtProperties.class);


    public String createdToken(String userId, String username, Object users) {
        String token = "";
        Long expiration = jwtProperties.getExpiration();
        if (rememberMe) {
            expiration = jwtProperties.getRememberMeExpiration();
        }
        if (jwtProperties.getUserSign()) {
            Assert.notNull(userSign, HttpStatus.UNAUTHORIZED.getCode(),
                    StrUtil.format(HttpStatus.UNAUTHORIZED.getMessage() + " {}", "找不到签名类和方法"));
            /**
             * 自定义key 动态实现 ，没有实现即"" 获取属性配置key
             */
            String key = userSign.AuthKey();
            if (StrUtil.isEmpty(key)) {
                key = jwtProperties.getSecret();
            }
            token = JwtUtil.createJWT(username, userId, users, expiration, userSign.sign(), key);
        } else {
            token = JwtUtil.createJWT(username, userId, users, expiration);
        }
        return token;
    }


    /**
     * 解析user token用户信息。
     *
     * @param userToken
     * @return
     */
    public <T> T parseUserToken(String userToken, Class<T> returnCls) {
        Jws<Claims> claims;
        try {
            if (jwtProperties.getUserSign()) {
                Assert.notNull(userSign, HttpStatus.UNAUTHORIZED.getCode(),
                        StrUtil.format(HttpStatus.UNAUTHORIZED.getMessage() + " {}", "找不到签名类和方法"));
                String key = userSign.AuthKey();
                if (StrUtil.isEmpty(key)) {
                    key = jwtProperties.getSecret();
                }
                claims = JwtUtil.parseJWT(userToken, userSign.getSignedKey(key));
            } else {
                claims = JwtUtil.parseJWT(userToken);
            }
        } catch (ExpiredJwtException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED_EXPIRED);
        }catch (SignatureException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED);
        }
        LinkedHashMap linkedHashMap = claims.getBody().get("user", LinkedHashMap.class);
        return BeanUtil.toBean(linkedHashMap, returnCls);

    }


    /**
     * 返回用户ID
     *
     * @param userToken
     * @return
     */
    public String getUserId(String userToken) {
        Claims claims = this.getClaims(userToken);
        return claims.getId();
    }

    /**
     * 返回用户名称
     *
     * @param userToken
     * @return
     */
    public String getUserName(String userToken) {
        Claims claims = this.getClaims(userToken);
        return claims.getSubject();
    }


    /**
     * 返回整个原始jwt信息
     *
     * @param jwt
     * @return
     */
    public Jws<Claims> parseJWT(String jwt) {
        Jws<Claims> claimsJws;
        try {
            if (jwtProperties.getUserSign()) {
                Assert.notNull(userSign, HttpStatus.UNAUTHORIZED.getCode(),
                        StrUtil.format(HttpStatus.UNAUTHORIZED.getMessage() + " {}", "找不到签名类和方法"));
                String key = userSign.AuthKey();
                if (StrUtil.isEmpty(key)) {
                    key = jwtProperties.getSecret();
                }
                claimsJws = JwtUtil.parseJWT(jwt, userSign.getSignedKey(key));
            } else {
                claimsJws = JwtUtil.parseJWT(jwt);
            }
        } catch (ExpiredJwtException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED_EXPIRED);
        }catch (SignatureException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED);
        }
        return claimsJws;
    }

    /**
     * 返回Claims主题信息
     *
     * @param jwt
     * @return
     */
    public Claims getClaims(String jwt) {
        Claims claims;
        try {
            if (jwtProperties.getUserSign()) {
                Assert.notNull(userSign, HttpStatus.UNAUTHORIZED.getCode(),
                        StrUtil.format(HttpStatus.UNAUTHORIZED.getMessage() + " {}", "找不到签名类和方法"));
                String key = userSign.AuthKey();
                if (StrUtil.isEmpty(key)) {
                    key = jwtProperties.getSecret();
                }
                claims = JwtUtil.getClaims(jwt, userSign.getSignedKey(key));
            } else {
                claims = JwtUtil.getClaims(jwt);
            }
        } catch (ExpiredJwtException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED_EXPIRED);
        } catch (SignatureException e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED);
        }
        return claims;
    }

}
