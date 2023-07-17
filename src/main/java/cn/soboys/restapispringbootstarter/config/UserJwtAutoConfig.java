package cn.soboys.restapispringbootstarter.config;

import cn.soboys.restapispringbootstarter.authorization.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/15 09:49
 * @webSite https://github.com/coder-amiao
 * 用户jwt token生成配置
 */

public class UserJwtAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public UserSign userSignWith() {
        return new UserSignWith();
    }

    @Bean
    @ConditionalOnMissingBean
    public LoginAuthorization loginAuthorizationSubject() {
        return new LoginAuthorizationSubject();
    }


    @Bean
    @ConditionalOnMissingBean
    public UserJwtToken userJwtToken(UserSignWith userSignWith) {
        UserJwtToken userJwtToken = new UserJwtToken();
        userJwtToken.setUserSign(userSignWith);
        return userJwtToken;
    }

}
