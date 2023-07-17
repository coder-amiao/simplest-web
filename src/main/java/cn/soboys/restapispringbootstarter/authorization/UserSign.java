package cn.soboys.restapispringbootstarter.authorization;

import cn.soboys.restapispringbootstarter.test.UserEntry;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/13 21:38
 * @webSite https://github.com/coder-amiao
 */
public interface UserSign {

    /**
     * 自定义签名
     * @return
     */
    public SignatureAlgorithm sign();

    /**
     * 自定义秘钥
     * @return
     */
    public String AuthKey();


    /**
     * 获取密钥
     *
     * @return Key
     */
    default Key getSignedKey(String key) {
        byte[] apiKeySecretBytes = DatatypeConverter
                .parseBase64Binary(key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,
                sign().getJcaName());
        return signingKey;
    }


}
