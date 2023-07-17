package cn.soboys.restapispringbootstarter.test.config;

import cn.soboys.restapispringbootstarter.authorization.LoginAuthorization;
import cn.soboys.restapispringbootstarter.authorization.UserJwtToken;
import cn.soboys.restapispringbootstarter.authorization.UserSignWith;
import cn.soboys.restapispringbootstarter.test.MyLoginAuthorization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/15 09:49
 * @webSite https://github.com/coder-amiao
 * 用户jwt token生成配置
 */
@Configuration
public class UserJwtConfig {

    /**
     * 自定义jwt token 签名生成
     *
     * @return
     */


    @Bean
    public UserSignWith userSignWith() {
        return new UserSignWith();
    }


    /**
     * 认证授权
     *
     * @return
     */
    @Bean
    public MyLoginAuthorization loginAuthorization() {
        return new MyLoginAuthorization();
    }

    @Bean
    public UserJwtToken userJwtToken(UserSignWith userSignWith) {
        UserJwtToken userJwtToken = new UserJwtToken();
        userJwtToken.setUserSign(userSignWith);
        return userJwtToken;
    }


}
