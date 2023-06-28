package cn.soboys.restapispringbootstarter.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/27 22:55
 * @webSite https://github.com/coder-amiao
 */
@Configuration
@ConfigurationProperties(prefix = "rest-api")
@Data
public class RestApiProperties {
    private boolean enabled;


    @Configuration
    @ConfigurationProperties(prefix = "rest-api.logging")
    @Data
   public class LoggingProperties {
        private String path;
    }


}
