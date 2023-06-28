package cn.soboys.restapispringbootstarter.config;

import cn.soboys.restapispringbootstarter.ApplicationRunner;
import cn.soboys.restapispringbootstarter.ExceptionHandler;
import cn.soboys.restapispringbootstarter.ResultHandler;
import cn.soboys.restapispringbootstarter.i18n.I18NMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/27 11:36
 * @webSite https://github.com/coder-amiao
 */
@Configuration
@ConditionalOnProperty(name = "rest-api.enabled", havingValue = "true")
public class BeanAutoConfiguration {


    @Bean
    public I18NMessage i18NMessage() {
        return new I18NMessage();
    }

    @Bean
    public ResultHandler resultHandler() {
        return new ResultHandler();
    }

    @Bean
    public ExceptionHandler exceptionHandler() {
        return new ExceptionHandler();
    }

    @Bean
    public StartupApplicationListener startupApplicationListener() {
        return new StartupApplicationListener();
    }


    @Bean
    public RestApiProperties restApiProperties() {
        return new RestApiProperties();
    }

    @Bean
    public RestApiProperties.LoggingProperties loggingProperties(RestApiProperties restApiProperties) {
        return restApiProperties.new LoggingProperties();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner();
    }
}
